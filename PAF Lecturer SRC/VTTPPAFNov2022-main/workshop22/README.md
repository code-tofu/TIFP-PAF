## Workshop 22

```
drop database if exists rsvpdb;

create database rsvpdb;

use rsvpdb;
```

```
CREATE USER 'rsvp'@'localhost' IDENTIFIED BY 'Password@123456';

GRANT ALL PRIVILEGES ON *.* TO 'rsvp'@'localhost' WITH GRANT OPTION;

FLUSH PRIVILEGES;
```

## Json data sample

```
{
	"name": "Kenneth",
	"email": "k@k.com",
	"phone": "3345677",
	"confirmation_date": "2018-12-10T13:49:51.141Z",
	"comments": "hi hi",
}
```

## Apply unique contraint on email column

```
ALTER TABLE rsvp ADD CONSTRAINT rsvp_email_u UNIQUE (email);
```

## Railway cloud

```
railway login
```

```
railway up
```

```
mysql -u root -p -h containers-us-west-53.railway.app --port=6099
```
