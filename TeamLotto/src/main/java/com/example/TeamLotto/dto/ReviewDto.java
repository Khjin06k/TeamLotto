package com.example.TeamLotto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReviewDto {
    List<ReviewDto> selectReviewList;
}
