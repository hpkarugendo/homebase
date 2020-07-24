package com.hpkarugendo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class HomebaseApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void replaceString(){
        String origin = "https://blahblahblah.org";
        String replacer = "https://homebase-3113.azureedge.net/";
        String toReplace = origin.substring(8, origin.length());
        System.out.println(toReplace);
        String replaced = replacer + toReplace;
        System.out.println("FINAL STRING IS: " + replaced);
        Assert.isTrue(replaced.length() > origin.length(), "New String Should be longer than old string!");
    }

}
