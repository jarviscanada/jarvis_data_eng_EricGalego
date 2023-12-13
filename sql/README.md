# Introduction

This project is designed to help users practice and study their SQL queries. It can be used as a reference for answers 
to the various questions asked. We utilize SQL queries specifically with the PostgreSQL RDBMS. To follow along with the
practice questions make sure to run clubdata.sql (Note: Do not run this and table setup as clubdata.sql creates these
tables for you.

# SQL Queries

##### Table Setup (DDL)

```sql
CREATE TABLE cd.members(
    memid INTEGER PRIMARY KEY NOT NULL,
    surname VARCHAR(200) NOT NULL,
    firstname VARCHAR(200) NOT NULL,
    address VARCHAR(300) NOT NULL,
    zipcode INTEGER NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    recommendedby INTEGER,
    CONSTRAINT fk_members_recommendedby FOREIGN KEY (recommendedby)
        REFERENCES cd.members(memid) ON DELETE SET NULL,
    joindate TIMESTAMP NOT NULL
);

CREATE TABLE cd.facilities(
    facid INTEGER PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL, 
    membercost NUMERIC NOT NULL, 
    guestcost NUMERIC NOT NULL, 
    initialoutlay NUMERIC NOT NULL, 
    monthlymaintenance NUMERIC NOT NULL
);

CREATE TABLE cd.bookings(
    bookid INTEGER PRIMARY KEY NOT NULL, 
    facid INTEGER NOT NULL, 
    memid INTEGER NOT NULL, 
    starttime TIMESTAMP NOT NULL,
    slots INTEGER NOT NULL,
    CONSTRAINT fk_bookings_facid FOREIGN KEY (facid) REFERENCES cd.facilities(facid),
    CONSTRAINT fk_bookings_memid FOREIGN KEY (memid) REFERENCES cd.members(memid)
);
```

### Modifying Data

#### Insert 
```sql
INSERT INTO cd.facilities 
VALUES 
  (9, 'Spa', 20, 30, 100000, 800);
```
#### Insert3 
```sql
INSERT INTO cd.facilities 
VALUES 
  (
    (
      SELECT 
        MAX(facid) 
      FROM 
        cd.facilities
    )+ 1, 
    'Spa', 
    20, 
    30, 
    100000, 
    800
  );
```
#### Update
```sql
UPDATE 
  cd.facilities 
SET 
  initialoutlay = 10000 
WHERE 
  facid = 1;
```
#### Update Calculated
```sql
UPDATE 
  cd.facilities 
SET 
  membercost = membercost * 1.1, 
  guestcost = guestcost * 1.1 
WHERE 
  facid = 1;
```

#### Delete
```sql
DELETE FROM 
  cd.bookings 
WHERE 
  facid IS NOT NULL;
```

#### Delete Condition
```sql
DELETE FROM 
  cd.members 
WHERE 
  memid = 37;
```

### Basics
#### Where 2
```sql
SELECT 
  facid, 
  name, 
  membercost, 
  monthlymaintenance 
FROM 
  cd.facilities 
WHERE 
  membercost > 0 
  AND membercost < 0.02 * monthlymaintenance;
```
#### Where 3
```sql
SELECT 
  * 
FROM 
  cd.facilities 
WHERE 
  name LIKE '%Tennis%';
```
#### Where 4
```sql
SELECT 
  * 
FROM 
  cd.facilities 
WHERE 
  facid IN(1, 5);
```
#### Date
```sql
SELECT 
  memid, 
  surname, 
  firstname, 
  joindate 
FROM 
  cd.members 
WHERE 
  joindate > '2012-09-01';
```
#### Union
```sql
SELECT 
  surname 
FROM 
  cd.members 
UNION 
SELECT 
  name 
FROM 
  cd.facilities;
```

### Join
#### Simple Join
```sql
SELECT 
  starttime 
FROM 
  cd.bookings 
  LEFT JOIN cd.members ON cd.members.memid = cd.bookings.memid 
WHERE 
  surname = 'Farrell' 
  AND firstname = 'David';
```
#### Simple Join 2
```sql
SELECT 
  starttime AS start, 
  name 
FROM 
  cd.facilities 
  LEFT JOIN cd.bookings ON cd.bookings.facid = cd.facilities.facid 
WHERE 
  TO_CHAR(starttime, 'YYYY-MM-DD')= '2012-09-21' 
  AND name LIKE 'Tennis Court%' 
ORDER BY 
  starttime;
```
#### Self 2
```sql
SELECT 
  mem.firstname AS memfname, 
  mem.surname AS memsname, 
  rec.firstname AS recfname, 
  rec.surname AS recsname 
FROM 
  cd.members mem 
  LEFT JOIN cd.members rec ON mem.recommendedby = rec.memid 
ORDER BY 
  memsname, 
  memfname;
```
#### Self
```sql
SELECT 
  DISTINCT recs.firstname, 
  recs.surname 
FROM 
  cd.members mem 
  JOIN cd.members recs ON mem.recommendedby = recs.memid 
ORDER BY 
  recs.surname, 
  recs.firstname;
```
#### Sub
```sql
SELECT 
  DISTINCT CONCAT(firstname, ' ', surname) AS member, 
  (
    SELECT 
      CONCAT(firstname, ' ', surname) AS recommender 
    FROM 
      cd.members recs 
    WHERE 
      recs.memid = mems.recommendedby
  ) 
FROM 
  cd.members mems 
ORDER BY 
  member;
```

### Aggregation
#### Count 3
```sql
SELECT 
  recommendedby, 
  COUNT(recommendedby) 
FROM 
  cd.members 
WHERE 
  recommendedby IS NOT NULL 
GROUP BY 
  recommendedby 
ORDER BY 
  recommendedby;
```
#### Fachours
```sql
SELECT 
  facid, 
  SUM(slots) AS "Total Slots" 
FROM 
  cd.bookings 
GROUP BY 
  facid 
ORDER BY 
  facid;
```
#### Fachours By Month
```sql
SELECT 
  facid, 
  SUM(slots) AS "Total Slots" 
FROM 
  cd.bookings 
WHERE 
  TO_CHAR(starttime, 'YYYY-MM')= '2012-09' 
GROUP BY 
  facid 
ORDER BY 
  "Total Slots";
```
#### Fachours By Month 2
```sql
SELECT 
  facid, 
  EXTRACT(
    'MONTH' 
    FROM 
      starttime
  ) AS month, 
  SUM(slots) AS "Total Slots" 
FROM 
  cd.bookings 
WHERE 
  EXTRACT(
    'YEAR' 
    FROM 
      starttime
  ) = '2012' 
GROUP BY 
  facid, 
  month;
```
#### Members 1
```sql
SELECT 
  COUNT(DISTINCT memid) 
FROM 
  cd.bookings;
```
#### N Booking
```sql
SELECT 
  surname, 
  firstname, 
  cd.bookings.memid, 
  MIN(cd.bookings.starttime) 
FROM 
  cd.members 
  LEFT JOIN cd.bookings ON cd.bookings.memid = cd.members.memid 
WHERE 
  cd.bookings.starttime > '2012-09-01' 
GROUP BY 
  surname, 
  firstname, 
  cd.bookings.memid 
ORDER BY 
  memid;
```
#### Count Members
```sql
SELECT 
  COUNT(*) OVER (), 
  firstname, 
  surname 
FROM 
  cd.members 
ORDER BY 
  joindate;
```
#### Num Members
```sql
SELECT 
  ROW_NUMBER() OVER (
    ORDER BY 
      joindate
  ) AS row_number, 
  firstname, 
  surname 
FROM 
  cd.members;
```
#### Fachours 4
```sql
SELECT 
  r.facid, 
  r.total 
FROM 
  (
    SELECT 
      facid, 
      SUM(slots) AS total, 
      RANK() OVER (
        ORDER BY 
          SUM(slots) DESC
      ) Rank 
    FROM 
      cd.bookings 
    GROUP BY 
      facid
  ) as r 
WHERE 
  rank = 1;
```

### String
#### Concat
```sql
SELECT 
  CONCAT(surname, ', ', firstname) AS name 
FROM 
  cd.members;
```
#### Reg
```sql
SELECT 
  memid, 
  telephone 
FROM 
  cd.members 
WHERE 
  telephone ~ '\(\d\d\d\)*';
```
#### Substr
```sql
SELECT 
  substring(
    surname 
    from 
      1 for 1
  ) AS letter, 
  COUNT(*) 
FROM 
  cd.members 
GROUP BY 
  letter 
ORDER BY 
  letter; 
```