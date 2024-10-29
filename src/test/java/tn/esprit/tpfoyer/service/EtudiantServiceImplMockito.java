package tn.esprit.tpfoyer.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceImplTest {
    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    private Etudiant etuA;
    private Etudiant etuB;
    private List<Etudiant> etuList;

    @BeforeEach
    void setUp() {
        etuA = new Etudiant();
        etuA.setIdEtudiant(1L);
        etuA.setNomEtudiant("Bousbih");
        etuA.setPrenomEtudiant("Oussama");
        etuA.setCinEtudiant(123456789);
        etuA.setDateNaissance(new Date());

        etuB = new Etudiant();
        etuB.setIdEtudiant(2L);
        etuB.setNomEtudiant("Ali");
        etuB.setPrenomEtudiant("Mohamed");
        etuB.setCinEtudiant(987654321);
        etuB.setDateNaissance(new Date());

        etuList = Arrays.asList(etuA, etuB);
    }

    @Test
    void testRetrieveAllEtudiants() {
        when(etudiantRepository.findAll()).thenReturn(etuList);
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveEtudiant() {
        Long etudiantId = 1L;
        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etuA));
        Etudiant result = etudiantService.retrieveEtudiant(etudiantId);
        assertNotNull(result);
        assertEquals("Bousbih", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).findById(etudiantId);
    }

    @Test
    void testAddEtudiant() {
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etuA);
        Etudiant result = etudiantService.addEtudiant(etuA);
        assertNotNull(result);
        assertEquals("Bousbih", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).save(etuA);
    }

    @Test
    void testModifyEtudiant() {
        etuA.setNomEtudiant("Bousbih-Modifié");
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etuA);
        Etudiant result = etudiantService.modifyEtudiant(etuA);
        assertNotNull(result);
        assertEquals("Bousbih-Modifié", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).save(etuA);
    }

    @Test
    void testRemoveEtudiant() {
        Long etudiantId = 1L;
        doNothing().when(etudiantRepository).deleteById(etudiantId);
        etudiantService.removeEtudiant(etudiantId);
        verify(etudiantRepository, times(1)).deleteById(etudiantId);
    }

    @Test
    void testRecupererEtudiantParCin() {
        long cin = 123456789L;
        when(etudiantRepository.findEtudiantByCinEtudiant(cin)).thenReturn(etuA);
        Etudiant result = etudiantService.recupererEtudiantParCin(cin);
        assertNotNull(result);
        assertEquals(cin, result.getCinEtudiant());
        verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(cin);
    }
}
