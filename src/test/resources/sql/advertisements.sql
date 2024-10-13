-- Create the advertisements table
CREATE TABLE IF NOT EXISTS advertisement
(
    id          UUID PRIMARY KEY,
    title       VARCHAR(255) NOT NULL UNIQUE,
    description TEXT         NOT NULL
);

-- Insert sample advertisements into the table
INSERT INTO advertisement (id, title, description)
VALUES ('f3bb6a21-3a84-42b6-ae64-1b6e908b6374', 'Google Ads', 'An advertisement for Google'),
       ('b98a1d56-57c2-44c9-85b7-dc3a54b88224', 'Bing Ads', 'An advertisement for Bing'),
       ('19d69df1-cb3c-4ef7-92d4-6121fa5e388b', 'Amazon Ads', 'An advertisement for Amazon'),
       ('b3d41415-80d3-4a78-a5a5-737be1699c4d', 'Facebook Ads', 'An advertisement for Facebook'),
       ('aeae8d3d-e4c9-4225-95f1-70750399e491', 'Twitter Ads', 'An advertisement for Twitter');
