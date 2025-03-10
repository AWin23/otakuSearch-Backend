# OtakuSearch Backend

About: Full-stack iOS mobile application that allows for users to search for their fav anime, along with upcoming anime as well. Allows them to save and book their list of which ones watched and which ones need to watch. Allows for “tag filtering” as well, with a search bar to loop up specific anime types. Utilizing a Swift frontend, a Java GraphQL backend will be doing the fetching to AniList GraphQL API, and storing its responses in the backend for the frontend to snag - less overhead for the frontend. 

## Instalation Steps

### Java Install
```sdk install java 17.0.9-tem```

#### Verifying Installation of Java


### Installing Spring Boot
```sdk install springboot```

#### Verifying Installation of SpringBoot
```spring --version```

### Installing Gradle
```gradle wrapper```
```./gradlew build```

### CD into your backend directory project if you haven't
```spring init --name=otakuSearch-backend --dependencies=web,graphql,cache,data-redis --type=gradle-project otakuSearch-backend```
This initializes the project

## References
