--Modifying Data
--Insert
INSERT INTO cd.facilities
VALUES
  (9, 'Spa', 20, 30, 100000, 800);
--Insert3
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
--Update
UPDATE
  cd.facilities
SET
  initialoutlay = 10000
WHERE
  facid = 1;
--Update Calculated
UPDATE
  cd.facilities
SET
  membercost = membercost * 1.1,
  guestcost = guestcost * 1.1
WHERE
  facid = 1;
--Delete
DELETE FROM
  cd.bookings
WHERE
  facid IS NOT NULL;
--Delete Condition
DELETE FROM
  cd.members
WHERE
  memid = 37;

--Basics
--Where 2
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
--Where 3
SELECT
  *
FROM
  cd.facilities
WHERE
  name LIKE '%Tennis%';
--Where 4
SELECT
  *
FROM
  cd.facilities
WHERE
  facid IN(1, 5);
--Date
SELECT
  memid,
  surname,
  firstname,
  joindate
FROM
  cd.members
WHERE
  joindate > '2012-09-01';
--Union
SELECT
  surname
FROM
  cd.members
UNION
SELECT
  name
FROM
  cd.facilities;

--Join
--Simple Join
SELECT
  starttime
FROM
  cd.bookings
  LEFT JOIN cd.members ON cd.members.memid = cd.bookings.memid
WHERE
  surname = 'Farrell'
  AND firstname = 'David';
--Simple Join 2
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
--Self 2
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
--Self
SELECT
  DISTINCT recs.firstname,
  recs.surname
FROM
  cd.members mem
  JOIN cd.members recs ON mem.recommendedby = recs.memid
ORDER BY
  recs.surname,
  recs.firstname;
--Sub
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

--Aggregation
--Count 3
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
--Fachours
SELECT
  facid,
  SUM(slots) AS "Total Slots"
FROM
  cd.bookings
GROUP BY
  facid
ORDER BY
  facid;
--Fachours By Month
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
--Fachours By Month 2
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
--Members 1
SELECT
  COUNT(DISTINCT memid)
FROM
  cd.bookings;
--N Booking
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
--Count Members
SELECT
  COUNT(*) OVER (),
  firstname,
  surname
FROM
  cd.members
ORDER BY
  joindate;
--Num Members
SELECT
  ROW_NUMBER() OVER (
    ORDER BY
      joindate
  ) AS row_number,
  firstname,
  surname
FROM
  cd.members;
--Fachours 4
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

--String
--Concat
SELECT
  CONCAT(surname, ', ', firstname) AS name
FROM
  cd.members;
--Reg
SELECT
  memid,
  telephone
FROM
  cd.members
WHERE
  telephone ~ '\(\d\d\d\)*';
--Substr
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