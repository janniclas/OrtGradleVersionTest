package iem.fraunhofer.de

import org.ossreviewtoolkit.analyzer.Analyzer
import org.ossreviewtoolkit.analyzer.PackageManagerFactory
import org.ossreviewtoolkit.analyzer.determineEnabledPackageManagers
import org.ossreviewtoolkit.model.ResolvedPackageCurations
import org.ossreviewtoolkit.model.config.AnalyzerConfiguration
import org.ossreviewtoolkit.model.config.OrtConfiguration
import org.ossreviewtoolkit.model.config.RepositoryConfiguration
import org.ossreviewtoolkit.model.utils.PackageCurationProvider
import org.ossreviewtoolkit.plugins.packagecurationproviders.api.PackageCurationProviderFactory
import org.ossreviewtoolkit.plugins.packagecurationproviders.api.SimplePackageCurationProvider
import java.io.File

data class DependencyAnalyzerConfig(
    val analyzerConfiguration: AnalyzerConfiguration,
    val enabledPackageManagers: Set<PackageManagerFactory>,
    val enabledCurationProviders: List<Pair<String, PackageCurationProvider>>,
    val repositoryConfiguration: RepositoryConfiguration
)

class DependencyAnalyzer(
    private val config: DependencyAnalyzerConfig = createDefaultConfig(),
    private val analyzer: Analyzer = Analyzer(config = config.analyzerConfiguration)
) {

    fun runAnalyzer(projectPath: File) {

        val managedFiles = analyzer.findManagedFiles(
            absoluteProjectPath = projectPath,
            packageManagers = config.enabledPackageManagers,
            repositoryConfiguration = config.repositoryConfiguration
        )

        val results = analyzer.analyze(managedFiles, config.enabledCurationProviders)
        println(results.analyzer)
    }

    companion object {
        private fun createDefaultConfig(): DependencyAnalyzerConfig {
            //TODO: check if we want to enable this
            // NPM failed to resolve dependencies for path 'package.json':
            // IllegalArgumentException: No lockfile found in '.'. This potentially
            // results in unstable versions of dependencies. To support this, enable
            // the 'allowDynamicVersions' option in 'config.yml'.
            val ortConfig = OrtConfiguration()
            val analyzerConfiguration = ortConfig.analyzer
            val repositoryConfiguration = RepositoryConfiguration()
            val enabledPackageManagers = analyzerConfiguration.determineEnabledPackageManagers()

            val enabledCurationProviders = buildList {
                val repositoryPackageCurations = repositoryConfiguration.curations.packages

                if (ortConfig.enableRepositoryPackageCurations) {
                    add(
                        ResolvedPackageCurations.REPOSITORY_CONFIGURATION_PROVIDER_ID
                                to
                                SimplePackageCurationProvider(repositoryPackageCurations)
                    )
                } else if (repositoryPackageCurations.isNotEmpty()) {
                    println {
                        "Existing package curations are not applied " +
                                "because the feature is disabled."
                    }
                }

                addAll(PackageCurationProviderFactory.create(ortConfig.packageCurationProviders))
            }

            return DependencyAnalyzerConfig(
                analyzerConfiguration = analyzerConfiguration,
                enabledPackageManagers = enabledPackageManagers,
                enabledCurationProviders = enabledCurationProviders,
                repositoryConfiguration = repositoryConfiguration
            )
        }
    }
}