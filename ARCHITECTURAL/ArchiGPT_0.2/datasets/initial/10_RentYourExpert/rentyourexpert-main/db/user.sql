CREATE DATABASE IF NOT EXISTS rentYourExpert;
USE rentYourExpert;

CREATE TABLE IF NOT EXISTS customer (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `username` varchar(50) NOT NULL, 
    `name` varchar(50) NOT NULL, 
    `surname` varchar(50) NOT NULL,
    `email` varchar(100) NOT NULL,
    `password` varchar(50) NOT NULL,
    `isAdmin` tinyint unsigned NOT NULL,
    `image_url` varchar(500) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS worker (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL, 
    `surname` varchar(50) NOT NULL,
    `profession` varchar(50) NOT NULL, 
    `location` varchar(50) NOT NULL, 
    `description` varchar(250) NOT NULL, 
    `email` varchar(100) NOT NULL, 
    `phone` BIGINT unsigned NOT NULL, 
    `address` varchar(250) NOT NULL, 
    `available` tinyint unsigned NOT NULL,  
    `password` varchar(50) NOT NULL,
    `image_url` varchar(500) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS request (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `customer_id` int unsigned NOT NULL,
    `worker_id` int unsigned NOT NULL, 
    `accepted` tinyint unsigned NOT NULL, 
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY(`customer_id`) REFERENCES customer(`id`) ON DELETE CASCADE,
    FOREIGN KEY(`worker_id`) REFERENCES worker(`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS questionanswer (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `customer_id` int unsigned NOT NULL,
    `worker_id` int unsigned NOT NULL, 
    `question` varchar(500) NOT NULL, 
    `answer` varchar(500),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY(`customer_id`) REFERENCES customer(`id`) ON DELETE CASCADE,
    FOREIGN KEY(`worker_id`) REFERENCES worker(`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS review (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `customer_id` int unsigned NOT NULL,
    `worker_id` int unsigned NOT NULL, 
    `description` varchar(500) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY(`customer_id`) REFERENCES customer(`id`) ON DELETE CASCADE,
    FOREIGN KEY(`worker_id`) REFERENCES worker(`id`) ON DELETE CASCADE
); 


INSERT INTO worker (name, surname, profession, location, description, email, phone, address, available, password, image_url)
VALUES
('John', 'Doe', 'Web Developer', 'New York', 'Experienced web developer with expertise in HTML, CSS, and JavaScript.', 'johndoe@example.com', 1234567890, '123 Main St, New York, NY 10001', 1, '0000','https://media.istockphoto.com/id/619636712/it/foto/ritratto-di-un-uomo-daffari.jpg?s=612x612&w=0&k=20&c=x7G6yqy8gjg7j2q58HayYTYR85TQKQBFFtL0pm4YA2k='),
('Jane', 'Smith', 'Graphic Designer', 'Los Angeles', 'Creative graphic designer with a passion for typography and color theory.', 'janesmith@example.com', 2345678901, '456 Oak St, Los Angeles, CA 90001', 1,'0000','https://piedmontmutual.com/wp-content/uploads/sites/18/2015/02/1-Business-Woman-300x286.png'),
('Bob', 'Johnson', 'Plumber', 'Chicago', 'Licensed plumber with over 10 years of experience in residential and commercial plumbing.', 'bobjohnson@example.com', 3456789012, '789 Maple St, Chicago, IL 60001', 1,'0000','https://www.24hourplumbingheating.com/uploads/1/3/9/7/139767902/plumbing-1_2.jpg'),
('Alice', 'Brown', 'Lawyer', 'Houston', 'Experienced lawyer specializing in corporate law and contract negotiations.', 'alicebrown@example.com', 4567890123, '012 Pine St, Houston, TX 70001', 1, '0000','https://media.licdn.com/dms/image/C4E03AQGfKaRBlN0WAQ/profile-displayphoto-shrink_400_400/0/1631125077816?e=1685577600&v=beta&t=zZmD6iR-2urGjMhYPL14T74i3ugexFJc5TT6yxrP0lY'),
('Tom', 'Lee', 'Electrician', 'San Francisco', 'Skilled electrician with expertise in wiring, circuitry, and electrical systems.', 'tomlee@example.com', 5678901234, '345 Cedar St, San Francisco, CA 20001', 1,'0000','https://www.familyhandyman.com/wp-content/uploads/2022/11/FHM-pro-electrician-GettyImages-1263444017-JVedit.jpg?fit=700,1024');


INSERT INTO customer (username, name, surname, email, password, isAdmin, image_url) 
VALUES 
('michaelScott', 'Michael', 'Scott', 'michaelscott@example.com', 'password123', 0,'https://exoffender.org/wp-content/uploads/2016/09/empty-profile.png'),
('janeDoe', 'Jane', 'Doe', 'janedoe@example.com', 'password456', 0,'https://exoffender.org/wp-content/uploads/2016/09/empty-profile.png'),
('adminUser', 'Admin', 'User', 'admin@example.com', 'admin123', 1,'https://exoffender.org/wp-content/uploads/2016/09/empty-profile.png'),
('bobSmith', 'Bob', 'Smith', 'bobsmith@example.com', 'password789', 0,'https://exoffender.org/wp-content/uploads/2016/09/empty-profile.png'),
('saraJohnson', 'Sara', 'Johnson', 'sarajohnson@example.com', 'password111', 0,'https://exoffender.org/wp-content/uploads/2016/09/empty-profile.png');


INSERT INTO request (customer_id, worker_id, accepted)
VALUES 
(1, 1, 0),
(2, 2, 0),
(3, 4, 0),
(4, 3, 0),
(5, 5, 0);

INSERT INTO questionanswer (customer_id, worker_id, question, answer)
VALUES 
(1, 2, 'Can you design a logo for my new business?', NULL),
(2, 3, 'Can you fix my leaky faucet?', NULL),
(3, 1, 'Can you create a responsive website for my business?', NULL),
(4, 4, 'Can you review this contract for me?', NULL),
(5, 5, 'Can you install a ceiling fan in my living room?', NULL);


INSERT INTO review (customer_id, worker_id, description)
VALUES
    (1, 1, 'This worker did an amazing job!'),
    (2, 2, 'Very professional and efficient.'),
    (4, 3, 'Highly recommend this worker!'),
    (3, 4, 'Exceeded my expectations.'),
    (5, 5, 'Great communication and attention to detail.');