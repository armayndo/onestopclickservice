INSERT INTO user(
   id
  ,email
  ,first_name
  ,is_enabled
  ,last_name
  ,password
  ,role
  ,username
) VALUES (
   1
  ,'admin@email.com'
  ,'admin'
  ,0
  ,'admin'  -- last_name - IN varchar(255)
  ,'$2a$10$xhKOrAwoT69DlF7TudM7DuDmMwse3FQKlDEJLY3kWFIxpB2g0ix9i '  -- password - IN varchar(255)
  ,'ADMIN'  -- role - IN varchar(255)
  ,'admin'  -- username - IN varchar(255)
)