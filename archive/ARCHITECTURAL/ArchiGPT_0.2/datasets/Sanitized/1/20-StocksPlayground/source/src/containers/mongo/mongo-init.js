rs.status();
db.createUser(
		{
			user: "admin",
			pwd: "password",
			roles: [
				{ role: "readWrite", db: "test" },
			]
		}
);

db.users.drop();
db.roles.drop();
db.data.drop();
db.createCollection("users");
db.createCollection("roles");
db.createCollection("data");
db.roles.insertMany([
	{ name: "ROLE_USER" },
	{ name: "ROLE_ADMIN" }
]);
