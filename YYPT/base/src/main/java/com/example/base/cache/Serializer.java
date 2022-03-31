package com.example.base.cache;

@SPI
public interface Serializer<SI, SRDI> {
    SRDI serialize(SI data);

    <DR> DR deSerialize(SRDI data);
}
