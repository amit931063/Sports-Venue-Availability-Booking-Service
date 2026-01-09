package com.stapubox.Stapubox.services;
import com.stapubox.Stapubox.entities.Sport;
import com.stapubox.Stapubox.repositories.SportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class SportsService {
    private final RestTemplate restTemplate;
    private final SportRepository sportsRepository;

    public Sport addSport(Sport sport) {
        return sportsRepository.save(sport);
    }
    public Sport getBySportCode(String code) {
        return sportsRepository.findBySportName(code)
                .orElseThrow(() -> new RuntimeException("Invalid sport code: " + code));
    }

    private final Map<String, String> sportCache = new ConcurrentHashMap<>();

    @Scheduled(fixedRate = 3600000)
    public void refreshSports() {
        try {
            log.info("Refreshing sports cache from external API...");

            Map[] response = restTemplate.getForObject("https://stapubox.com/sportslist/", Map[].class);

            if (response != null) {
                sportCache.clear();
                for (Map s : response) {

                    String code = String.valueOf(s.get("sport_code"));
                    String name = String.valueOf(s.get("sport_name"));
                    sportCache.put(code, name);
                }
                log.info("Successfully cached {} sports.", sportCache.size());
            }
        } catch (Exception e) {
            log.error("External API is down! System will use the last known successful cache. Error: {}", e.getMessage());
        }
    }



    public boolean isValidSport(String sportCode) {

        if (sportCache != null && sportCache.containsKey(sportCode)) {
            return true;
        }


        return sportsRepository.findBySportName(sportCode).isPresent();
    }
}
