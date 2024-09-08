import com.abi.convention.ExtensionType
import com.abi.convention.addUiLayerDependencies
import com.abi.convention.configureAndroidCompose
import com.abi.convention.configureBuildTypes
import com.abi.convention.configureKotlinAndroid
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

class AndroidFeatureUiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("runningTracker.android.library.compose")
            }

            dependencies {
                addUiLayerDependencies(target)
            }
        }
    }
}