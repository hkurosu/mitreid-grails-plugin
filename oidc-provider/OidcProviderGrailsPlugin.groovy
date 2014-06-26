import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor

class OidcProviderGrailsPlugin {
    // the plugin version
    def version = "0.1-SNAPSHOT"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.3 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Oidc Provider Plugin" // Headline display name of the plugin
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
                'url-pattern'("/*")
            }
        }

        // TODO: make spring security filter works!
/*
        def filters = xml.filter
        def lastFilter = filters[filters.size() - 1]
        lastFilter + {
            filter {
                'filter-name'('springSecurityFilterChain')
                'filter-class'('org.springframework.web.filter.DelegatingFilterProxy')
                'init-param' {
                    'param-name'('contextAttribute')
                    'param-value'('org.springframework.web.servlet.FrameworkServlet.CONTEXT.spring')
                }
            }
        }

        def filterMappings = xml.'filter-mapping'
        def lastFilterMapping = filterMappings[filterMappings.size()-1]
        lastFilterMapping + {
            'filter-mapping' {
                'filter-name'('springSecurityFilterChain')
                'url-pattern'("/*")
            }
        }
*/

    }

    def doWithSpring = {
        // borrowed codes from http://grails.org/plugin/springmvc
        // TODO: check if these blocks are necessary?

        def config = application.config.oidc

        mvcLocaleChangeInterceptor(LocaleChangeInterceptor)

        def handlerInterceptors = [ref('mvcLocaleChangeInterceptor')]
        config.interceptors.each { interceptor ->
            handlerInterceptors << ref(interceptor)
        }

//        mvcHandlerMapping(BeanNameUrlHandlerMapping) {
//            detectHandlersInAncestorContexts = true
//            order = 1
//            interceptors = handlerInterceptors
//        }

//        mvcViewResolver(UrlBasedViewResolver) {
//            viewClass = GrailsSpringMvcView
//            order = 1
//            prefix = '/WEB-INF/jsp/'
//            suffix = '.jsp'
//        }

//        handlerExceptionResolver(SimpleMappingExceptionResolver) {
//            order = 1
//            defaultErrorView = config.defaultErrorView ?: 'error' // default to WEB-INF/error.jsp
//            if (config.exceptionMappings) {
//                exceptionMappings = new Properties()
//                config.exceptionMappings.each { key, value ->
//                    exceptionMappings.setProperty key, value
//                }
//            }
//        }

//        def componentPackages = config.componentPackages
//        if (componentPackages instanceof Collection) {
//            xmlns grailsContext:'http://grails.org/schema/context'
//            grailsContext.'component-scan'('base-package': componentPackages.join(','))
//        }
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
