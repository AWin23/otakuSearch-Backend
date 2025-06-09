# OtakuSearch Backend

About: Full-stack iOS mobile application that allows for users to search for their fav anime, along with upcoming anime as well. Allows them to save and book their list of which ones watched and which ones need to watch. Allows for “tag filtering” as well, with a search bar to loop up specific anime types. Utilizing a Swift frontend, a Java GraphQL backend will be doing the fetching to AniList GraphQL API, and storing its responses in the backend for the frontend to snag - less overhead for the frontend. 

[![View the System Design](assets/otaku_diagram.png)](https://lucid.app/lucidchart/4e2c25a4-8c30-4e80-8813-5b3cbb1d8fe8/edit?viewport_loc=-1135%2C-1467%2C5997%2C3105%2C0_0&invitationId=inv_09ba86ba-aee0-49b6-98a3-040bdfd963a6)


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

### Run the Server with:
```./gradlew bootRun```


### CD into your backend directory project if you haven't
```spring init --name=otakuSearch-backend --dependencies=web,graphql,cache,data-redis --type=gradle-project otakuSearch-backend```
This initializes the project

## References
