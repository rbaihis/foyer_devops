package tn.esprit.tpfoyer.service;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.annotation.CrossOrigin;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("University Service Testing for ")
class UniversiteServiceImplTest {
    @InjectMocks
    UniversiteServiceImpl service;
    @Mock
    UniversiteRepository repository;

    private List<Universite> universiteList ;


    @BeforeEach
    public void initData(){
        universiteList = new ArrayList<>();
        universiteList.add(
                new Universite(1L,"Esprit","ghazela",new Foyer(1L,"espritFoyer",150,null,null))
        );
        universiteList.add(
                new Universite(2L,"TekUp","ghazela",new Foyer(2L,"TekupFoyer",100,null,null))
        );
    }

    @Test
    @DisplayName("Retrieve All University Methods")
    void TestRetrieveAllUniversites() {
        when(repository.findAll()).thenReturn(universiteList);
        assertEquals(2,service.retrieveAllUniversites().size());
    }

    @Test
    @DisplayName("Retrieve by Id University Methods")
    void TestRetrieveUniversite() {
        when(repository.findById(anyLong())).thenAnswer(invoc ->{
            Long id = invoc.getArgument(0);
            return universiteList.stream().filter(u->u.getIdUniversite()==id ).findAny();
        } );

        // case exist
        assertEquals(1L, service.retrieveUniversite(1L).getIdUniversite(), "successfully getting the object");
        // case not exist
        assertNull(service.retrieveUniversite(10L),"equal null since id match no object in db" );
    }

    @Test
    @DisplayName("Add University Methods")
    void TestAddUniversite() {
        Universite universite = Universite.builder().idUniversite(-99999L).nomUniversite("ISG").adresse("Bouchoucha").foyer(new Foyer(3L,"IsgFoyer",50,null,null)).build();
        when(repository.save(any(Universite.class))).thenAnswer(invocationOnMock -> {
            Universite savedUniversity = invocationOnMock.getArgument(0);
            savedUniversity.setIdUniversite(universiteList.size()+1);
            return savedUniversity;
        });

        assertNotEquals(0,service.addUniversite(universite).getIdUniversite(),"successfully getting value different then default not explicitly assigned '0' on repository.save() call");

    }

    @Test
    @DisplayName("Modify University Methods")
    void TestModifyUniversite() {
        Universite universiteInput = Universite.builder().idUniversite(1L).nomUniversite("ISG").adresse("Bouchoucha").foyer(new Foyer(3L,"IsgFoyer",50,null,null)).build();
        when(repository.save(any(Universite.class))).thenAnswer(invocationOnMock -> {
            Universite newData = invocationOnMock.getArgument(0);
            universiteList.forEach(u->{
                if(u.getIdUniversite() == newData.getIdUniversite()){
                    u.setNomUniversite(newData.getNomUniversite());
                    u.setAdresse(newData.getAdresse());
                    u.setFoyer(newData.getFoyer());
                }
            });
            return universiteList.stream().filter(u->u.getIdUniversite()==newData.getIdUniversite()).findFirst().orElse(newData);
        });

        Universite modifiedUniversity=service.modifyUniversite(universiteInput);
        assertEquals(universiteInput.getNomUniversite(),modifiedUniversity.getNomUniversite(),"name should match , even if exist or not since logic does not take it in consideration here in service");
    }

    @Test
    @DisplayName("Delete University Methods By Id, list size is 2 initially")
    void testRemoveUniversite() {
        doAnswer(invocationOnMock -> {
            Long id= invocationOnMock.getArgument(0);
            universiteList.removeIf(u->u.getIdUniversite()==id);
            return null;
        }).when(repository).deleteById(any());

        service.removeUniversite(2L);
        assertEquals(1, universiteList.size() , "size should become 1, since id exist");
        service.removeUniversite(3L);
        assertEquals(1, universiteList.size() , "size should still be 1, since id does not exist");
    }
}