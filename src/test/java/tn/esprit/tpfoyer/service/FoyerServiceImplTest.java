package tn.esprit.tpfoyer.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class FoyerServiceImplTest {
    @Mock
    FoyerRepository foyerRepository;
    @InjectMocks
    FoyerServiceImpl foyerService;

    Foyer foyer = Foyer.builder().nomFoyer("foyer esprit").capaciteFoyer(150).build();
    List<Foyer> listFoyer = new ArrayList<>() {
        {
            add(new Foyer(1L, "foyer esb", 100));
            add(new Foyer(2L, "foyer prepa", 50));
        }
    };

    @Test
    void testRetrieveFoyer(){

        when(foyerRepository.findById(anyLong()))
                .thenReturn(Optional.of(foyer));
        Foyer foyer1 = foyerService.retrieveFoyer(1L);
        Assertions.assertNotNull(foyer1);
    }

    @Test
    void testAddFoyer() {
        Foyer foyersaved = Foyer.builder().idFoyer(1L).nomFoyer("foyer esprit").capaciteFoyer(150).build();

        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyersaved);
        Foyer foyer1 = foyerService.addFoyer(foyer);

        Assertions.assertNotNull(foyer1);
        Assertions.assertEquals(1L, foyer1.getIdFoyer());

        verify(foyerRepository).save(foyer);
    }

    @Test
    void testretrieveAllFoyers() {
        when(foyerRepository.findAll()).thenReturn(listFoyer);
        Assertions.assertEquals(2,foyerService.retrieveAllFoyers().size());
    }


    @Test
    void retrieveFoyer() {
        when(foyerRepository.findById(anyLong()))
                .thenReturn(listFoyer.stream()
                        .filter(f -> f.getIdFoyer().equals(1L))
                        .findFirst());
        Assertions.assertEquals(1,foyerService.retrieveFoyer(1L).getIdFoyer());
    }

    @Test
    void modifyFoyer() {
        Foyer tryfoyer = Foyer.builder().idFoyer(1L).nomFoyer("sesame").capaciteFoyer(10).build();
        when(foyerRepository.save(any(Foyer.class))).thenAnswer(inv -> {
            inv.getArgument(0);
            listFoyer.get(0).setNomFoyer(tryfoyer.getNomFoyer());
            listFoyer.get(0).setCapaciteFoyer(tryfoyer.getCapaciteFoyer());

            return listFoyer.get(0) ;
        });
        foyerService.modifyFoyer(tryfoyer);
        Assertions.assertEquals("sesame",listFoyer.get(0).getNomFoyer());

    }

    @Test
    void removeFoyer() {

        Long foyerid = 1L;
        foyerService.removeFoyer(foyerid);
        verify(foyerRepository).deleteById(foyerid);
    }
}
