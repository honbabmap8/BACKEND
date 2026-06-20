INSERT INTO subway_stations
(station_id, station_name, rest_lat, rest_lng)
VALUES
    (1, '삼각지역', 37.5345, 126.9733);

INSERT INTO features (feature_id, feature_name, feature_detail, feature_img) VALUES (1, '1인석 있음', '바 테이블 다수', '/img/img1.svg');
INSERT INTO features (feature_id, feature_name, feature_detail, feature_img) VALUES (2, '1인 메뉴 가능', '1인 메뉴 ok', '/img/img2.svg');
INSERT INTO features (feature_id, feature_name, feature_detail, feature_img) VALUES (3, '혼밥 난이도', '혼밥 만족도 높음', '/img/img3.svg');
INSERT INTO features (feature_id, feature_name, feature_detail, feature_img) VALUES (4, '빠른 회전율', '혼밥 30분 코스', '/img/img4.svg');
INSERT INTO features (feature_id, feature_name, feature_detail, feature_img) VALUES (5, '시선 부담', '혼자 있기 편함', '/img/img5.svg');
INSERT INTO features (feature_id, feature_name, feature_detail, feature_img) VALUES (6, '타겟층 추천', '혼밥 입문 추천', '/img/img6.svg');
INSERT INTO features (feature_id, feature_name, feature_detail, feature_img) VALUES (7, '혼밥 분위기', '조용한 분위기', '/img/img7.svg');
INSERT INTO features (feature_id, feature_name, feature_detail, feature_img) VALUES (8, '회전율', '빠른 식사 가능', '/img/img8.svg');
INSERT INTO features (feature_id, feature_name, feature_detail, feature_img) VALUES (9, '이용 목적', '혼자 힐링하기 좋음', '/img/img9.svg');
INSERT INTO features (feature_id, feature_name, feature_detail, feature_img) VALUES (10, '재방문율', '단골이 많은 곳', '/img/img10.svg');

INSERT INTO restaurants
(rest_id, rest_name, rest_level, station_id, rest_distance, rest_lat, rest_lng, representative_menu, rest_detail, image_url, created_at)
VALUES
    (1, '마녀김밥 삼각지점', 1, 1, 117, 37.536394, 126.972577, '마녀 김밥, 묵참 김밥', '분식', 'https://buly.kr/9tD1kVr', NOW()),
    (2, '명화원', 2, 1, 64, 37.536349, 126.974633, '자장면, 짬뽕', '중식', 'https://buly.kr/6te7DBT', NOW()),
    (3, '101번지남산돈까스 삼각지점', 2, 1, 47, 37.534894, 126.974834, '돈까스누들맵떡, 남산치킨까스', '돈까스', 'https://buly.kr/7x8f66W', NOW()),
    (4, '대원식당', 4, 1, 141, 37.534295, 126.974721, '생선구이백반, 두루치기백반', '연탄불 향 가득한 생선구이의 진수', 'https://buly.kr/A47kruM', NOW()),
    (5, '멜팅팟', 3, 1, 162, 37.534432, 126.974419, '전복 내장 파스타, 토마토 꿀 가자미', '퀄리티가 보장된 인생 맛집', 'https://buly.kr/D3gdXoW', NOW()),
    (6, '더수피', 4, 1, 155, 37.536004, 126.97198, '솔티드 카라멜 프렌치토스트, 할라피뇨엔쵸비파스타', '깔끔한 음식과 분위기 좋은 브런치 카페', 'https://buly.kr/90dCz3r', NOW()),
    (7, '맘스터치 삼각지역점', 1, 1, 15, 37.536254, 126.973284, '싸이버거 세트, 빅싸이순살', '햄버거', 'https://image.gsncoupon.com:34443//202311/09/20231109PD1699518473731241.jpg', NOW()),
    (8, '한강버거', 3, 1, 100, 37.53484, 126.974897, '새우버거, 한강 BBQ 버거', '오동통 새우가 씹히는 새우버거의 매력', 'https://buly.kr/5fEqCwR', NOW()),
    (9, '치히로 서울용산점', 2, 1, 254, 37.532925, 126.973398, '에비텐동, 사케동', '바삭한 텐동과 함께하는 행복한 한 끼', 'https://buly.kr/AlmmnGE', NOW()),
    (10, '이아초밥 용산점', 5, 1, 217, 37.531819, 126.972146, '회전초밥, 우동정식', '회전초밥으로 만나는 신선한 맛', 'https://buly.kr/GvpJ7pL', NOW()),
    (11, '스시투고', 5, 1, 99, 37.536331, 126.972826, '모듬초밥, 불초밥+미니우동', '불향 가득 입힌 인기 초밥의 매력', 'https://buly.kr/883Q53Y', NOW()),
    (12, '마토미', 5, 1, 190, 37.537453, 126.974355, '수아데로 타코, 트리파스 타코', '곱창타코로 느끼는 멕시코의 진한 맛', 'https://buly.kr/AwhZdQ7', NOW()),
    (13, '부국정', 3, 1, 110, 37.534624, 126.974317, '양곰탕, 한우육회비빔밥', '진한 국물의 해장국이 인기인 삼각지 대표 맛집', 'https://buly.kr/1GLrpGK', NOW()),
    (14, '덕순이감자탕', 4, 1, 118, 37.534137, 126.973893, '뼈해장국, 우거지감자탕', '깔끔한 국물맛의 묵은지감자탕', 'https://buly.kr/G3FY55C', NOW()),
    (15, '골목식당', 3, 1, 119, 37.534482, 126.974356, '순두부찌개, 청국장', '요일별 정식 맛집', 'https://buly.kr/B7cIkyU', NOW());

INSERT INTO rest_feat (rest_feat_id, rest_id, feat_id)
VALUES
    (1, 1, 2),
    (2, 1, 3),

    (3, 2, 7),
    (4, 2, 6),

    (5, 3, 1),
    (6, 3, 9),

    (7, 4, 10),
    (8, 4, 4),

    (9, 5, 5),
    (10, 5, 8),

    (11, 6, 9),
    (12, 6, 10),

    (13, 7, 3),
    (14, 7, 7),

    (15, 8, 4),
    (16, 8, 8),

    (17, 9, 8),
    (18, 9, 1),

    (19, 10, 4),
    (20, 10, 7),

    (21, 11, 9),
    (22, 11, 10),

    (23, 12, 6),
    (24, 12, 5),

    (25, 13, 7),
    (26, 13, 2),

    (27, 14, 3),
    (28, 14, 8),

    (29, 15, 10),
    (30, 15, 3);


-- 1. 혼밥 정도 (6개)
INSERT INTO TAGS (TAG_ID, TAG_NAME) VALUES (1, '혼자 앉기 편해요');
INSERT INTO TAGS (TAG_ID, TAG_NAME) VALUES (2, '눈치 안 보여요');
INSERT INTO TAGS (TAG_ID, TAG_NAME) VALUES (3, '늦게까지 해요');
INSERT INTO TAGS (TAG_ID, TAG_NAME) VALUES (4, '조용해서 좋아요');
INSERT INTO TAGS (TAG_ID, TAG_NAME) VALUES (5, '회전이 빨라요');
INSERT INTO TAGS (TAG_ID, TAG_NAME) VALUES (6, '식탁 간격이 넓어요');

-- 2. 메뉴/서비스 (6개)
INSERT INTO TAGS (TAG_ID, TAG_NAME) VALUES (7, '메뉴가 다양해요');
INSERT INTO TAGS (TAG_ID, TAG_NAME) VALUES (8, '가성비가 좋아요');
INSERT INTO TAGS (TAG_ID, TAG_NAME) VALUES (9, '포장도 가능해요');
INSERT INTO TAGS (TAG_ID, TAG_NAME) VALUES (10, '1인 메뉴가 있어요');
INSERT INTO TAGS (TAG_ID, TAG_NAME) VALUES (11, '양이 적당해요');
INSERT INTO TAGS (TAG_ID, TAG_NAME) VALUES (12, '웨이팅이 없어요');