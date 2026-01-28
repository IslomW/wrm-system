package com.sharom.wrm.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoxLabelDto {
    private String clientCode;
    private String boxGroupCode;
    private String description;
    private String l;
    private String w;
    private String h;
    private String weight;
    private String qrSvg;

}
