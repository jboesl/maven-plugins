package com.github.goldin.plugins.jenkins


abstract class Task
{
    private final String space = ' ' * 8
    abstract List<String> getPropertyNames()

    final String getHudsonClass()
    {
        "hudson.tasks.${ this.class.simpleName }"
    }

    final String getMarkup()
    {
        List<String> lines = []

        for ( property in propertyNames )
        {
            String value = this[ property ] as String
            if ( value ) { lines << "<$property>${ value.trim() }</$property>" }
        }

        space + lines.join( "\n$space" )
    }
}


@SuppressWarnings( 'StatelessClass' )
class Shell extends Task
{
    String command

    List<String> getPropertyNames(){[ 'command' ]}
}


@SuppressWarnings( 'StatelessClass' )
class BatchFile extends Task
{
    String command

    List<String> getPropertyNames(){[ 'command' ]}
}


@SuppressWarnings( 'StatelessClass' )
class Ant extends Task
{
    String antName
    String targets
    String antOpts    = ''
    String buildFile  = 'build.xml'
    String properties = ''

    List<String> getPropertyNames(){[ ( antName ? 'antName' : '' ),
                                      'targets', 'antOpts', 'buildFile', 'properties' ].grep() }
}


@SuppressWarnings( 'StatelessClass' )
class Maven extends Task
{
    String  targets              = '-B -e clean install'
    String  mavenName            = '(Default)'
    String  jvmOptions           = ''
    String  pom                  = 'pom.xml'
    String  properties           = ''
    boolean usePrivateRepository = false

    List<String> getPropertyNames(){[ 'targets', 'mavenName', 'jvmOptions',
                                      ( pom == 'false' ? '' : 'pom' ),
                                      'properties', 'usePrivateRepository' ].grep() }
}