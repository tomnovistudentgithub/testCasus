package nl.maastrichtuniversity.myusc.service;


import nl.maastrichtuniversity.myusc.model.Sport;
import nl.maastrichtuniversity.myusc.dtos.SportDto;
import nl.maastrichtuniversity.myusc.repository.SportRepository;
import org.springframework.stereotype.Service;

@Service
public class SportService {

    SportRepository sportRepository;

    public SportService(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    public void addSport(SportDto sportDto) {
        sportRepository.findByNameIgnoreCase(sportDto.getName())
                .ifPresent(s -> {
                    throw new RuntimeException("Sport with name " + sportDto.getName() + " already exists");
                });
        Sport sport = new Sport();
        sport.setName(sportDto.getName());
        sport.setDescription(sportDto.getDescription());
        sport.setSportType(sportDto.getSportType());
        sportRepository.save(sport);
    }

    public void deleteSport(Long id) {
        if (!sportRepository.existsById(id)) {
            throw new RuntimeException("Sport with id " + id + " does not exist");
        }
        sportRepository.deleteById(id);
    }
}
