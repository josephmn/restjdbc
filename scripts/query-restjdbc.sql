---------------------------------------
-- TABLA SERVER PERSON MICROSERVICES --
---------------------------------------
IF OBJECT_ID ('person') IS NOT NULL
	BEGIN
		DROP TABLE person
	END
GO

IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'person') AND type in (N'U'))
	BEGIN
		CREATE TABLE person
		(
		id int NOT NULL,
		name varchar(100),
		lastname varchar(100),
		age int
		)
	END
GO

IF EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'ClusteredIndex0' AND type in (1,2))
	BEGIN
		DROP INDEX [ClusteredIndex0] ON person WITH ( ONLINE = OFF )
	END
GO

CREATE CLUSTERED INDEX [ClusteredIndex0] ON person
(
[ID] ASC
)
GO

IF EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'NonClusteredIndex1' AND type in (1,2))
	BEGIN
		DROP INDEX [NonClusteredIndex1] ON person WITH ( ONLINE = OFF )
	END
GO

CREATE UNIQUE NONCLUSTERED INDEX [NonClusteredIndex1] ON person
(
name ASC,
lastname ASC
)
GO

------------------
-- EXAMPLE DATA --
------------------
insert into person (id, name, lastname, age)
values
(NEXT VALUE FOR person_seq, 'Joseph','Magallanes',32),
(NEXT VALUE FOR person_seq, 'Jose','Huertas',21),
(NEXT VALUE FOR person_seq, 'Javier','Huertas',25),
(NEXT VALUE FOR person_seq, 'Richard','Alvarez',25),
(NEXT VALUE FOR person_seq, 'Joel','Vasquez',27),
(NEXT VALUE FOR person_seq, 'Kevin','Cardenas',27),
(NEXT VALUE FOR person_seq, 'Jose','Pacherres',25),
(NEXT VALUE FOR person_seq, 'Edward','Chavez',22),
(NEXT VALUE FOR person_seq, 'Ronal','Crisostomo',33),
(NEXT VALUE FOR person_seq, 'Gilmar','Lam',31)

---------------
-- SEQUENCES --
---------------
IF EXISTS (select 1 from sys.sequences where object_id = object_id('person_seq'))
	BEGIN
		DROP SEQUENCE person_seq
	END
GO

CREATE SEQUENCE person_seq
AS [numeric](27, 0)
MINVALUE 1 
MAXVALUE 999999999999999999999999999 
INCREMENT BY 1 
START WITH 1 
CACHE 20      
NO CYCLE ;

-----------------------------------------
-- PROCEDURE person --
-----------------------------------------
--- #### 1 SP
IF EXISTS (select 1 from sys.objects where name='person_all' and type = 'P')
	BEGIN
		DROP PROCEDURE person_all
	END
GO

CREATE PROCEDURE person_all
AS
-- exec person_all
BEGIN
	SELECT id, name, lastname, age 
	FROM person
END 
GO

--- #### 2 SP
IF EXISTS (select 1 from sys.objects where name='person_sel' and type = 'P')
	BEGIN
		DROP PROCEDURE person_sel
	END
GO

CREATE PROCEDURE person_sel
(
@id int
)
AS
-- exec person_sel 1
BEGIN
	SELECT id, name, lastname, age 
	FROM person
	WHERE id = @id
END 
GO

--- #### 3 SP
IF EXISTS (select 1 from sys.objects where name='person_ins' and type = 'P')
	BEGIN
		DROP PROCEDURE person_ins
	END
GO

CREATE PROCEDURE person_ins
(
@name nvarchar(100),
@lastname nvarchar(100),
@age int
)
AS
-- exec person_ins 'Pedro', 'Jimenez', 36
BEGIN
	DECLARE @id int = (NEXT VALUE FOR person_seq)

	INSERT INTO person (id, name, lastname, age)
	VALUES (@id, @name, @lastname, @age)

	SELECT id, name, lastname, age 
	FROM person
	WHERE id = @id
END 
GO

--- #### 4 SP
IF EXISTS (select 1 from sys.objects where name='person_upd' and type = 'P')
	BEGIN
		DROP PROCEDURE person_upd
	END
GO

CREATE PROCEDURE person_upd
(
@id int,
@name nvarchar(100),
@lastname nvarchar(100),
@age int
)
AS
-- exec person_upd 13, 'Samuel', 'Hernandez Pacheco', 29
BEGIN
	UPDATE person
	SET 
	name = @name,
	lastname = @lastname,
	age = @age
	WHERE id = @id

	SELECT id, name, lastname, age 
	FROM person
	WHERE id = @id
END 
GO

--- #### 5 SP
IF EXISTS (select 1 from sys.objects where name='person_del' and type = 'P')
	BEGIN
		DROP PROCEDURE person_del
	END
GO

CREATE PROCEDURE person_del
(
@id int
)
AS
-- exec person_del 4
BEGIN
	DELETE FROM person WHERE id = @id
END 
GO

--- #### 6 SP
IF EXISTS (select 1 from sys.objects where name='person_sel_name' and type = 'P')
	BEGIN
		DROP PROCEDURE person_sel_name
	END
GO

CREATE PROCEDURE person_sel_name
(
@name varchar(100)
)
AS
-- exec person_sel_name 'J'
BEGIN
	SELECT id, name, lastname, age 
	FROM person
	WHERE name LIKE '%' + @name + '%'
END 
GO
