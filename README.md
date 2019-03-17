# City Suggester

Coveo backend coding challenge. Project instructions can be found [here](https://github.com/coveo/backend-coding-challenge).

## Setup

Before anything, you must clone/download the repository.

### Docker

1. Make sure you have [docker](https://docs.docker.com/install/#supported-platforms) installed.
2. Make sure you have [docker-compose](https://docs.docker.com/compose/install/#install-compose) installed.
3. From the root directory, run `docker-compose up`.
    - you can add the `-d` or `--detach` option to run containers in the background.

You should now be able to connect to the following:

- backend server: [http://localhost:8080/](http://localhost:8080/)
- postgres server: localhost:5432

## API

```text
GET /suggestions
```

### Query Parameters
| Parameter | Required | Description                             |
| --------- | :------: | --------------------------------------- |
| q         |   Yes    | The name the city starts with.          |
| latitude  |   Yes    | The latitude of your current location.  |
| longitude |   Yes    | The longitude of your current location. |

### Sample Requests

#### Near Match

##### Request
```text
GET /suggestions?q=Londo&latitude=43.70011&longitude=-79.4163
```

##### Response

```json
{
    "suggestions": [
        {
            "name": "London, Ontario, Canada",
            "latitude": "42.98339080810547",
            "longitude": "-81.23303985595703",
            "score": 0.1855211690544582
        },
        {
            "name": "London, KY, USA",
            "latitude": "37.12897872924805",
            "longitude": "-84.08325958251953",
            "score": 0.005128510962492697
        },
        {
            "name": "Londontowne, MD, USA",
            "latitude": "38.933448791503906",
            "longitude": "-76.54940795898438",
            "score": 0.005553343714487146
        },
        {
            "name": "London, OH, USA",
            "latitude": "39.886451721191406",
            "longitude": "-83.44824981689453",
            "score": 0.006642495607016312
        },
        {
            "name": "Londonderry, NH, USA",
            "latitude": "42.865089416503906",
            "longitude": "-71.37394714355469",
            "score": 0.006968368987265614
        }
    ]
}
```

#### No Match

##### Request

```text
GET /suggestions?q=SomeRandomCityInTheMiddleOfNowhere&latitude=43.70011&longitude=-79.4163
```

##### Response

```json
{
  "suggestions": []
}
```