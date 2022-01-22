import random

def makeInsertAddressQuery():
    def getFirstName():
        names = [
            "Daisy",
            "Goofy",
            "Minnie",
            "Mickey",
            "Pooh",
            "Tigger",
            "Piglet",
            "Eeyore",
            "Donald",
            "Harry",
            "Anakin",
            "Luke",
            "Bilbo",
            "Jack",
            "Frodo",
            "Homer",
            "Bart",
            "Marge",
            "Lisa",
            "Maggie"
        ]
        return random.choice(names)

    def getLastName():
        names = [
            "Duck",
            "Mouse",
            "Bear",
            "Donkey",
            "Tiger",
            "Skywalker",
            "Simpson",
            "Baggins",
            "Sparrow",
            "Potter",
            "Laurier",
            "Francis",
            "Viotto",
            "Katz",
            "Barlowe",
            "Amana",
            "Ashford",
            "Boone",
            "Hale",
            "Langston",
            "Whitlock",
            "Levisay",
            "Yarbrough"
        ]
        return random.choice(names)

    def getAddress1():
        randomNumber = str(random.randint(1, 9999))
        streetNames = [
            "Windflower Lane",
            "Wild Goose Road",
            "Tall Grass Circle",
            "Blue Mountain Loop",
            "Lionheart Drive",
            "Three Stars Road",
            "Stones Throw Lane",
            "Sapphire Dragon Street",
            "Frying Pan Alley",
            "Margarita Road",
            "Field Daisy Lane",
            "Chardonnay Drive",
            "Golden Trout Way",
            "East Princess Boulevard",
            "Sir Galahad Court",
            "South Coral Honeysuckle Loop",
            "Melody Drive",
            "Mistletoe Lane",
            "Golden Trout Way",
            "Loch Ness Road",
            "Gentle Rain Drive",
            "Meditation Lane",
            "Grandiose Drive",
            "Ben Hur Road",
            "Loganberry Lane",
            "Sunflower",
            "Birds Nest Lane"
        ]
        return randomNumber + " " + random.choice(streetNames)

    def getAddress2():
        randomNumber = random.randint(1,10)
        if randomNumber > 7:
            addresses = [
                "Apt 1",
                "Apt 2",
                "Apt 3",
                "Apt 4",
                "Apt 5",
                "Apt 6",
                "Apt 7",
                "Apt 8",
                "Apt 9",
            ]
            return random.choice(addresses)
        else:
            return "None"

    def getCity():
        towns = [
            "Atkinson",
            "Wylie",
            "Marana",
            "Towson",
            "Indianapolis",
            "Metairie",
            "Redding",
            "Bozeman",
            "Macungie",
            "Atlanta",
            "Marrieta",
            "Baton Rouge",
            "Cypress",
            "Lake Forest",
            "San Clemente",
            "Temecula"
            "Scottsdale",
            "East Northport",
            "Jacksonville",
            "Wilmington",
            "Newbury Park",
            "Miami",
            "Sevierville",
            "Imperial Beach",
            "Richland",
            "Edmond",
            "Riverhead"
        ]
        return random.choice(towns)

    def getState():
        state = [
            "NH",
            "TX",
            "AZ",
            "MD",
            "IN",
            "LA",
            "CA",
            "MT",
            "PA",
            "MI",
            "FL",
            "NC",
            "TN",
            "WA",
            "OH",
            "OK",
            "NY",
            "NJ",
            "IL",
            "WI",
            "MI",
            "IA",
            "MN",
            "SC",
            "OR",
            "KS",
            "VA",
            "WV"
        ]
        return random.choice(state)

    def getZipCode():
        return str(random.randint(11111,99999))

    def getEmail():
        address = [
            "me",
            "someone",
            "first.name",
            "somebody",
            "oneperson",
            "self",
            "mymail",
            "addressee",
            "mailbox",
            "user",
            "username1",
            "mynamegoeshere",
            "thisisme",
            "writeme"
        ]
        subdomain = [
            "me",
            "mailme",
            "myaddress",
            "mymailbox",
            "myemail",
            "email",
            "letters",
            "address",
            "somedomain",
            "fancydomainname",
            "secretmail",
            "addressbox"
        ]
        domain = [
            ".com",
            ".net",
            ".mail",
            ".org",
            ".edu",
            ".ninja",
            ".island",
            ".code",
            ".me",
            ".co.ca",
            ".it"
        ]

        return random.choice(address) + "@" + random.choice(subdomain) + random.choice(domain)
        firstName = getFirstName();
    firstName = getFirstName();
    lastName = getLastName();
    address1 = getAddress1();
    address2 = getAddress2();
    city = getCity();
    state = getState();
    zipCode = getZipCode();
    email = getEmail();
    if address2 == "None":
        query = f'INSERT INTO customer(first_name, last_name, address1, city, state, zip_code, email) VALUES ("{firstName}", "{lastName}", "{address1}", "{city}", "{state}", "{zipCode}", "{email}");'
    else:
        query = f'INSERT INTO customer(first_name, last_name, address1, address2, city, state, zip_code, email) VALUES ("{firstName}", "{lastName}", "{address1}", "{address2}", "{city}", "{state}", "{zipCode}", "{email}");'
    print(query)


# Generates a query that creates fake bank accounts for users
def makeInsertAccountsQuery(customer_id):

    # Decide on number of accounts the person has
    numAccounts = random.randint(0, 5)

    # Get statement to insert that many and link it to that customer id
    for i in range(1, numAccounts):
        dollar = random.randrange(0, 100000)
        cents = (random.randrange(0, 99) * 0.01)
        balance = dollar + cents
        type = random.choice(["checking", "savings"])
        query = f'INSERT INTO account (customer_id, balance, type) VALUES ({customer_id}, {balance}, "{type}");'
        print(query)


if __name__ == "__main__":
    for i in range (1,51):
        makeInsertAddressQuery()
        makeInsertAccountsQuery(i)