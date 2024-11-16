package nl.maastrichtuniversity.myusc.service;


import nl.maastrichtuniversity.myusc.model.Sport;
import nl.maastrichtuniversity.myusc.dtos.SportDto;
import nl.maastrichtuniversity.myusc.repository.SportRepository;
import org.springframework.stereotype.Service;

@Service
public class SportService {

    private final SportRepository sportRepository;

    public SportService(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    public void addSport(SportDto sportDto) {
        validateSportDoesNotExist(sportDto.getName());
        Sport sport = createSportFromDto(sportDto);
        sportRepository.save(sport);
    }

    private void validateSportDoesNotExist(String name) {
        sportRepository.findByNameIgnoreCase(name)
                .ifPresent(s -> {
                    throw new RuntimeException("Sport with name " + name + " already exists");
                });
    }

    private Sport createSportFromDto(SportDto sportDto) {
        Sport sport = new Sport();
        sport.setName(sportDto.getName());
        sport.setSportType(sportDto.getSportType());
        return sport;
    }

    public void deleteSport(Long id) {
        validateSportExists(id);
        sportRepository.deleteById(id);
    }

    private void validateSportExists(Long id) {
        if (!sportRepository.existsById(id)) {
            throw new RuntimeException("Sport with id " + id + " does not exist");
        }
    }
}
