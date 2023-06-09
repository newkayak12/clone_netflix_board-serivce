package com.netflix_clone.boardservice.component.constants;

import com.netflix_clone.boardservice.repository.domains.Banner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component(value = "constants")
public class Constants {


    public static String EMAIL;
    public static String EMAIL_KEY;
    public static Integer BANNER_MAXIMUM_COUNT;

    @Value(value = "${constants.banner_maximum_count}")
    public void setBannerMaximumCount(Integer _BANNER_MAXIMUM_COUNT) { BANNER_MAXIMUM_COUNT = _BANNER_MAXIMUM_COUNT; }
    @Value("${constants.email}")
    public void setEmail(String _EMAIL) { EMAIL = _EMAIL; }
    @Value("${constants.email_key}")
    public void setEmailKey(String _EMAIL_KEY) { EMAIL_KEY = _EMAIL_KEY; }


}