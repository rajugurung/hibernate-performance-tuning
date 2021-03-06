package com.demo.nplusone.lazy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineDto {
    private Long id;
    private String lineNbr;
    
}
