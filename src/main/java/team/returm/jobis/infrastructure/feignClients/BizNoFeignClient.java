package team.returm.jobis.infrastructure.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team.returm.jobis.infrastructure.feignClients.dto.BusinessNumberResponse;

@FeignClient(name = "BizNo", url = "https://bizno.net/api/fapi")
public interface BizNoFeignClient {

    @PostMapping
    BusinessNumberResponse getApi(@RequestParam("key") String key, @RequestParam("gb") Integer gb,
                                  @RequestParam("status") String status, @RequestParam("q") String bno,
                                  @RequestParam("type") String type);
}
