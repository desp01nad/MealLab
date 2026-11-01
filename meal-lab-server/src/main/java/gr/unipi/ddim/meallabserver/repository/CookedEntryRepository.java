package gr.unipi.ddim.meallabserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.unipi.ddim.meallabserver.entity.CookedEntry;

public interface CookedEntryRepository extends JpaRepository<CookedEntry, Long> {

    List<CookedEntry> findAllByOrderByCookedAtDesc();
}
