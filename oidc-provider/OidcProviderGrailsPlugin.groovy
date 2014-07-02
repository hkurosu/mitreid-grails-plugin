import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.view.JstlView
import org.springframework.web.servlet.view.UrlBasedViewResolver

class OidcProviderGrailsPlugin {
    // the plugin version
    def version = "0.1-SNAPSHOT"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.3 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def loadAfter = ['views']

    // TODO Fill in these fields
    def title = "OIDC Provider Plugin" // Headline display name of the plugin
    def author = "Your name"
    def authorEmail = ""
    def description = '''\
Brief summary/description of the plugin.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/oidc-provider"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    def doWithWebDescriptor = { xml ->

        def config = application.config.oidc
        if (config.separateServlet) {

            def servlets = xml.servlet
            def lastServlet = servlets[servlets.size() - 1]
            lastServlet + {
                servlet {
                    'servlet-name'('oidc')
                    'servlet-class'('org.springframework.web.servlet.DispatcherServlet')
                    'load-on-startup'(1)
                    'init-param' {
                        'param-name'('detectAllHandlerExceptionResolvers')
                        'param-value'('false')
                    }
                }
            }

            def mappings = xml.'servlet-mapping'
            def lastMapping = mappings[mappings.size() - 1]
            lastMapping + {
                'servlet-mapping' {
                    'servlet-name'('oidc')
                    'url-pattern'("/resources/*")
                    'url-pattern'("/.well-known/openid-configuration")
                    'url-pattern'("/home")
                    'url-pattern'("/about")
                    'url-pattern'("/authorize/*")
                    'url-pattern'("/token/*")
                    'url-pattern'("/userinfo/*")
                    'url-pattern'("/jwk/*")
                    'url-pattern'("/introspect/*")
                    'url-pattern'("/revoke/*")
                    'url-pattern'("/oauth/*")
                    'url-pattern'("/register/*")
                }
            }

            //
            // Spring security filter for MITREid endpoints
            //
            def filters = xml.filter
            def lastFilter = filters[filters.size() - 1]
            lastFilter + {
                filter {
                    'filter-name'('springSecurityFilterChain')
                    'filter-class'('org.springframework.web.filter.DelegatingFilterProxy')
                    'init-param' {
                        'param-name'('contextAttribute')
                        'param-value'('org.springframework.web.servlet.FrameworkServlet.CONTEXT.oidc')
                    }
                }
            }

            def filterMappings = xml.'filter-mapping'
            def lastFilterMapping = filterMappings[filterMappings.size() - 1]
            lastFilterMapping + {
                'filter-mapping' {
                    'filter-name'('springSecurityFilterChain')
                    'url-pattern'("/*")
                }
            }

        } else {

            //
            // *** (Option 2) Adding MITREid's endpoints to Grails Servlet
            //
            // Adding MITREid's Endpoints url patterns
            def servletMapping = xml.'servlet-mapping'.find {
                it.'servlet-name'.text() == "grails"
            }
            def urlPatterns = servletMapping.'url-pattern'
            def lastUrlPattern = urlPatterns[urlPatterns.size() - 1]
            lastUrlPattern + {
                'url-pattern'("/resources/*")
                'url-pattern'("/.well-known/openid-configuration")
                'url-pattern'("/home")
                'url-pattern'("/about")
                'url-pattern'("/authorize/*")
                'url-pattern'("/token/*")
                'url-pattern'("/userinfo/*")
                'url-pattern'("/jwk/*")
                'url-pattern'("/introspect/*")
                'url-pattern'("/revoke/*")
                'url-pattern'("/oauth/*")
                'url-pattern'("/register/*")
            }

            //
            // Spring security filter for MITREid endpoints
            //
            // FIXME: When turning on the security filter, /about won't work
            // exception message: No WebApplicationContext found: no ContextLoaderListener registered?
//            def filters = xml.filter
//            def lastFilter = filters[filters.size() - 1]
//            lastFilter + {
//                filter {
//                    'filter-name'('springSecurityFilterChain')
//                    'filter-class'('org.springframework.web.filter.DelegatingFilterProxy')
//                    'init-param' {
//                        'param-name'('contextAttribute')
//                        'param-value'('org.springframework.web.servlet.FrameworkServlet.CONTEXT.spring')
//                    }
//                }
//            }
//
//            def filterMappings = xml.'filter-mapping'
//            def lastFilterMapping = filterMappings[filterMappings.size() - 1]
//            lastFilterMapping + {
//                'filter-mapping' {
//                    'filter-name'('springSecurityFilterChain')
//                    'url-pattern'("/*")
//                }
//            }
        }
    }

    def doWithSpring = {

        def config = application.config.oidc
        if (config.separateServlet) {
            jspViewResolver(UrlBasedViewResolver) {
                viewClass = JstlView
                order = 1
                prefix = '/WEB-INF/views/'
                suffix = '.jsp'
            }
        } else {
            // Needs with (Option 2). Otherwise, comments out
            importBeans('classpath:/spring/application-context.xml')
        }

    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { ctx ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
