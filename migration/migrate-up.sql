CREATE TABLE `api_keys` (
    `key` VARCHAR(255) NOT NULL,
    `client` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`key`)
);

CREATE TABLE `logging` (
    `id` int NOT NULL AUTO_INCREMENT,
    `description` varchar(255) NOT NULL,
    `IP` char(255) NOT NULL,
    `endpoint` varchar(255) NOT NULL,
    `requested_at` timestamp NOT NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO api_keys VALUES
('1a5b0c4d8e1f8f6a9b0e0b0a6c9f4e4d3c9i8b3a7f6f0d5g4h9i6j8b3c9o0p8b', 'rest service'),
('7a3f6f0d5g4h9i6j8b3c9o0p8b5e9f3f1d6a81a5b0c4d8e1f8f6a9b0e0b0a6c9', 'php app service');

CREATE TABLE `subscriptions` (
  `user_id` int NOT NULL,
  `album_id` int NOT NULL,
  `status` enum('PENDING','REJECTED','ACCEPTED') NOT NULL DEFAULT 'PENDING'
);

CREATE TABLE `favorites` (
  `user_id` int NOT NULL,
  `album_id` int NOT NULL
);

CREATE TABLE `token` (
    `token_id` int NOT NULL AUTO_INCREMENT,
    `token_string` varchar(255) NOT NULL,
    `coin_value` int NOT NULL,
    PRIMARY KEY (`token_id`)
);

INSERT INTO `token` (`token_string`, `coin_value`) VALUES
('kbl-12345', 100),
('kbl-23456', 200);
('kbl-34567', 10);
('kbl-45678', 20);
('kbl-56789', 30);