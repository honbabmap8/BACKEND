package com.honbabmap.backend.restaurant.service;

import com.honbabmap.backend.restaurant.repository.*;
import com.honbabmap.backend.review.repository.ReviewRepository;
import com.honbabmap.backend.review.repository.ReviewTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    // Repository
    private final RestaurantRepository restaurantRepository;
    private final StationRepository stationRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewTagRepository reviewTagRepository;
}
