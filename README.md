# karaf-osgi-list-issue

The osgi:list command reports the state of the bundle is in "Waiting" state even after it recovers.

Steps to reproduce on Karaf 2.4.3
# Install Camel
karaf@root> features:chooseurl camel 2.16.1
Adding feature url mvn:org.apache.camel.karaf/apache-camel/2.16.1/xml/features
karaf@root> features:install camel

# Install sample bundles
karaf@root> install -s mvn:com.pronoia.karaf/service-interface/1.0.0-SNAPSHOT  mvn:com.pronoia.karaf/service-implementation/1.0.0-SNAPSHOT mvn:com.pronoia.karaf/service-consumer/1.0.0-SNAPSHOT

# Verify running
karaf@root> osgi:list
START LEVEL 100 , List Threshold: 50
   ID   State         Blueprint      Spring    Level  Name
[  58] [Active     ] [            ] [       ] [   50] camel-core (2.16.1)
[  59] [Active     ] [            ] [       ] [   50] camel-catalog (2.16.1)
[  73] [Active     ] [            ] [       ] [   50] geronimo-jta_1.1_spec (1.1.1)
[  74] [Active     ] [            ] [       ] [   50] camel-spring (2.16.1)
[  75] [Active     ] [Created     ] [       ] [   50] camel-blueprint (2.16.1)
[  76] [Active     ] [            ] [       ] [   50] camel-commands-core (2.16.1)
[  77] [Active     ] [Created     ] [       ] [   50] camel-karaf-commands (2.16.1)
[  78] [Active     ] [            ] [       ] [   80] Karaf Issue :: Service Interface (1.0.0.SNAPSHOT)
[  79] [Active     ] [Created     ] [       ] [   80] Karaf Issue :: Service Implementation (1.0.0.SNAPSHOT)
[  80] [Active     ] [Created     ] [       ] [   80] Karaf Issue :: Service Consumer (1.0.0.SNAPSHOT)


# Stop Service Implementation to force Service Consumer into "Waiting" state
karaf@root> stop service-implementation
karaf@root> osgi:list
START LEVEL 100 , List Threshold: 50
   ID   State         Blueprint      Spring    Level  Name
[  58] [Active     ] [            ] [       ] [   50] camel-core (2.16.1)
[  59] [Active     ] [            ] [       ] [   50] camel-catalog (2.16.1)
[  73] [Active     ] [            ] [       ] [   50] geronimo-jta_1.1_spec (1.1.1)
[  74] [Active     ] [            ] [       ] [   50] camel-spring (2.16.1)
[  75] [Active     ] [Created     ] [       ] [   50] camel-blueprint (2.16.1)
[  76] [Active     ] [            ] [       ] [   50] camel-commands-core (2.16.1)
[  77] [Active     ] [Created     ] [       ] [   50] camel-karaf-commands (2.16.1)
[  78] [Active     ] [            ] [       ] [   80] Karaf Issue :: Service Interface (1.0.0.SNAPSHOT)
[  79] [Resolved   ] [            ] [       ] [   80] Karaf Issue :: Service Implementation (1.0.0.SNAPSHOT)
[  80] [Active     ] [Waiting     ] [       ] [   80] Karaf Issue :: Service Consumer (1.0.0.SNAPSHOT)


# Start Service Implementation - Service Consumer remains in "Waiting" state even though route is running
karaf@root> start service-implementation
karaf@root> osgi:list
START LEVEL 100 , List Threshold: 50
   ID   State         Blueprint      Spring    Level  Name
[  58] [Active     ] [            ] [       ] [   50] camel-core (2.16.1)
[  59] [Active     ] [            ] [       ] [   50] camel-catalog (2.16.1)
[  73] [Active     ] [            ] [       ] [   50] geronimo-jta_1.1_spec (1.1.1)
[  74] [Active     ] [            ] [       ] [   50] camel-spring (2.16.1)
[  75] [Active     ] [Created     ] [       ] [   50] camel-blueprint (2.16.1)
[  76] [Active     ] [            ] [       ] [   50] camel-commands-core (2.16.1)
[  77] [Active     ] [Created     ] [       ] [   50] camel-karaf-commands (2.16.1)
[  78] [Active     ] [            ] [       ] [   80] Karaf Issue :: Service Interface (1.0.0.SNAPSHOT)
[  79] [Active     ] [Created     ] [       ] [   80] Karaf Issue :: Service Implementation (1.0.0.SNAPSHOT)
[  80] [Active     ] [Waiting     ] [       ] [   80] Karaf Issue :: Service Consumer (1.0.0.SNAPSHOT)

Steps to reproduce on Karaf 3.0.5
# Install Camel
karaf@root()> feature:repo-add camel 2.16.1
Adding feature url mvn:org.apache.camel.karaf/apache-camel/2.16.1/xml/features
karaf@root()> feature:install camel

# Install sample bundles
karaf@root> install -s mvn:com.pronoia.karaf/service-interface/1.0.0-SNAPSHOT  mvn:com.pronoia.karaf/service-implementation/1.0.0-SNAPSHOT mvn:com.pronoia.karaf/service-consumer/1.0.0-SNAPSHOT

# Verify running
karaf@root()> bundle:list
START LEVEL 100 , List Threshold: 50
ID | State  | Lvl | Version        | Name                                 
--------------------------------------------------------------------------
70 | Active |  50 | 2.16.1         | camel-core                           
71 | Active |  50 | 2.16.1         | camel-catalog                        
86 | Active |  50 | 1.1.1          | geronimo-jta_1.1_spec                
87 | Active |  50 | 2.16.1         | camel-spring                         
88 | Active |  50 | 2.16.1         | camel-blueprint                      
89 | Active |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Interface     
90 | Active |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Implementation
91 | Active |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Consumer  

# Stop Service Implementation to force Service Consumer into "Waiting" state
karaf@root> stop service-implementation
karaf@root> bundle:list
START LEVEL 100 , List Threshold: 50
ID | State    | Lvl | Version        | Name                                 
----------------------------------------------------------------------------
70 | Active   |  50 | 2.16.1         | camel-core                           
71 | Active   |  50 | 2.16.1         | camel-catalog                        
86 | Active   |  50 | 1.1.1          | geronimo-jta_1.1_spec                
87 | Active   |  50 | 2.16.1         | camel-spring                         
88 | Active   |  50 | 2.16.1         | camel-blueprint                      
89 | Active   |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Interface     
90 | Resolved |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Implementation
91 | Waiting  |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Consumer 

# Start Service Implementation - Service Consumer remains in "Waiting" state even though route is running
karaf@root> start service-implementation
karaf@root> bundle:list
START LEVEL 100 , List Threshold: 50
ID | State   | Lvl | Version        | Name                                 
---------------------------------------------------------------------------
70 | Active  |  50 | 2.16.1         | camel-core                           
71 | Active  |  50 | 2.16.1         | camel-catalog                        
86 | Active  |  50 | 1.1.1          | geronimo-jta_1.1_spec                
87 | Active  |  50 | 2.16.1         | camel-spring                         
88 | Active  |  50 | 2.16.1         | camel-blueprint                      
89 | Active  |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Interface     
90 | Active  |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Implementation
91 | Waiting |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Consumer

Steps to reproduce on Karaf 4.0.4

# Install Camel
karaf@root()> feature:repo-add camel 2.16.1
Adding feature url mvn:org.apache.camel.karaf/apache-camel/2.16.1/xml/features
karaf@root()> feature:install camel

# Install sample bundles
karaf@root> install -s mvn:com.pronoia.karaf/service-interface/1.0.0-SNAPSHOT  mvn:com.pronoia.karaf/service-implementation/1.0.0-SNAPSHOT mvn:com.pronoia.karaf/service-consumer/1.0.0-SNAPSHOT

# Verify running
karaf@root()> bundle:list
START LEVEL 100 , List Threshold: 50
ID | State  | Lvl | Version        | Name
--------------------------------------------------------------------------
52 | Active |  50 | 2.16.1         | camel-blueprint
53 | Active |  50 | 2.16.1         | camel-catalog
54 | Active |  80 | 2.16.1         | camel-commands-core
55 | Active |  50 | 2.16.1         | camel-core
56 | Active |  50 | 2.16.1         | camel-spring
57 | Active |  80 | 2.16.1         | camel-karaf-commands
58 | Active |  50 | 1.1.1          | geronimo-jta_1.1_spec
77 | Active |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Interface
78 | Active |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Implementation
79 | Active |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Consumer

# Stop Service Implementation to force Service Consumer into "Waiting" state
karaf@root> stop service-implementation
karaf@root> bundle:list
START LEVEL 100 , List Threshold: 50
ID | State    | Lvl | Version        | Name
----------------------------------------------------------------------------
52 | Active   |  50 | 2.16.1         | camel-blueprint
53 | Active   |  50 | 2.16.1         | camel-catalog
54 | Active   |  80 | 2.16.1         | camel-commands-core
55 | Active   |  50 | 2.16.1         | camel-core
56 | Active   |  50 | 2.16.1         | camel-spring
57 | Active   |  80 | 2.16.1         | camel-karaf-commands
58 | Active   |  50 | 1.1.1          | geronimo-jta_1.1_spec
77 | Active   |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Interface
78 | Resolved |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Implementation
79 | Waiting  |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Consumer

# Start Service Implementation - Service Consumer remains in "Waiting" state even though route is running
karaf@root> start service-implementation
karaf@root> bundle:list
START LEVEL 100 , List Threshold: 50
ID | State   | Lvl | Version        | Name
---------------------------------------------------------------------------
52 | Active  |  50 | 2.16.1         | camel-blueprint
53 | Active  |  50 | 2.16.1         | camel-catalog
54 | Active  |  80 | 2.16.1         | camel-commands-core
55 | Active  |  50 | 2.16.1         | camel-core
56 | Active  |  50 | 2.16.1         | camel-spring
57 | Active  |  80 | 2.16.1         | camel-karaf-commands
58 | Active  |  50 | 1.1.1          | geronimo-jta_1.1_spec
77 | Active  |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Interface
78 | Active  |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Implementation
79 | Waiting |  80 | 1.0.0.SNAPSHOT | Karaf Issue :: Service Consumer