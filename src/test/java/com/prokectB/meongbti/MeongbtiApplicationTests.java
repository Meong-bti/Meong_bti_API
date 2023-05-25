package com.prokectB.meongbti;

import com.prokectB.meongbti.dto.common.vault.VaultProps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(VaultProps.class) //추가
class MeongbtiApplicationTests {


}
