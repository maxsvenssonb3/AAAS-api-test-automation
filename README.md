# Overview
This project contains automated API tests that are posted to Azure DevOps pipeline.
On mvn install - downloads an artifact from AzureDevops containing schemas and client API code needed to run tests.

# Configuration
## pom.xml
Configure the following settings in the `pom.xml` file:

```xml
<project>
   <!-- Not important for this project since we don't build artifact -->
   <groupId>ORGANIZATION-NAME</groupId>
   <artifactId>ARTIFACT-NAME</artifactId>
   <version>ARTIFACT-VERSION</version>

   <properties>
   <artifact.feed.name>ARTIFACT-FEED-NAME</artifact.feed.name> <!-- AzureDevops artifact feed name -->
   <artifact.feed.url> <!-- AzureDevops build artifact feed url -->
      https://pkgs.dev.azure.com/ORGANIZATION-NAME/PROJECT-NAME/_packaging/ARTIFACT-FEED-NAME/maven/v1
   </artifact.feed.url>
   <organization.name>ORGANIZATION-NAME</organization.name> <!-- AzureDevops organization name -->
   <artifact.name>SCHEMA-AND-API-CLIENT-CODE-ARTIFACT-NAME</artifact.name> <!-- AzureDevops artifact name -->
   <artifact.version>API-CLIENT-CODE-AND-SCHEMA-ARTIFACT-VERSION</artifact.version> <!-- AzureDevops artifact version -->
   <maven.compiler.source>use-same-version-in-pipeline</maven.compiler.source> <!-- Java version -->
   <maven.compiler.target>use-same-version-in-pipeline</maven.compiler.target> <!-- Java version -->
      <!-- ... -->
   </properties>
   <!-- ... -->
</project>
```
## RestApiClientBuilder.kt & Constants.kt
Configure with correct API connection details.

## YAML File
In the YAML file, ensure that the Java versions are in sync with the Java versions in the pom.xml:
```yaml
variables:
  mavenJavaVersion: '1.17'
```

## Azure DevOps Build Artifact Feed Authorization
If you already have a local `settings.xml` with a PAT to AzureDevops Build Artifact feed, skip step 1 & 2.
1. **Create a Personal Access Token (PAT) with read and write access to the Azure DevOps artifact feed:**
   1. Go to your Azure DevOps organization and click on your profile picture in the top right corner.
   2. Click on "Security".
   3. Under "Personal access tokens", click "New Token".
   4. Give your token a name, an expiration date, and the necessary permissions.
   5. Copy the generated token and keep it secure, as you won't be able to see it again.

2. **Create a `settings.xml` file with your PAT as follows:**
```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">
   <servers>
      <server>
         <id>ARTIFACT-FEED-NAME</id>
         <username>ORGANIZATION-NAME</username>
         <password>PERSONAL-ACCESS-TOKEN</password>
      </server>
   </servers>
</settings>
```

3. Create a `.m2` directory and place the `settings.xml` inside
```
cd
mkdir ~/.m2
```

# Execution

## Run Tests Locally
1. Run command `mvn clean install`

## Run Tests in Azure Pipeline
1. Create a pipeline in Azure DevOps from the project YAML file.
2. Run the pipeline.
3. After the pipeline runs successfully, view test results in Azure UI

## Troubleshooting
1. Delete directory `Repository` in `.m2` directory
2. Sync maven files
3. Run command `mvn clean install`
