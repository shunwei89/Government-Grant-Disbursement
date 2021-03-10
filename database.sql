CREATE DATABASE IF NOT EXISTS assessment;
USE assessment;

DROP TABLE IF EXISTS household;
CREATE TABLE household (HOUSEHOLD_ID INT(11) NOT NULL, 
						HOUSEHOLD_TYPE VARCHAR(20) NOT NULL, 
                        PRIMARY KEY (HOUSEHOLD_ID));

DROP TABLE IF EXISTS member;
CREATE TABLE member (MEMBER_ID INT(11) NOT NULL, 
					 MEMBER_NAME VARCHAR(20) NOT NULL, 
                     GENDER VARCHAR(20) NOT NULL,
                     MARITAL_STATUS VARCHAR(20) NULL,
                     SPOUSE VARCHAR(20) NULL,
                     OCCUPATION_TYPE VARCHAR(20) NULL,
                     ANNUAL_INCOME INT(11) NULL,
                     DOB DATETIME NULL,
                     HOUSEHOLD_ID INT(11) NOT NULL,
                     PRIMARY KEY (MEMBER_ID),
                     CONSTRAINT FK_MemberHousehold FOREIGN KEY(HOUSEHOLD_ID) REFERENCES household(HOUSEHOLD_ID));