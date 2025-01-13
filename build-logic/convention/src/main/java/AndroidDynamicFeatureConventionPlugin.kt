import com.abi.convention.ExtensionType
import com.abi.convention.addUiLayerDependencies
import com.abi.convention.configureAndroidCompose
import com.abi.convention.configureBuildTypes
import com.abi.convention.configureKotlinAndroid
import com.abi.convention.libs
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.DynamicFeatureExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidDynamicFeatureConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.dynamic-feature")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<DynamicFeatureExtension> {

                configureKotlinAndroid(this)
                configureAndroidCompose(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.DYNAMIC_FEATURE
                )
            }

            dependencies {
                addUiLayerDependencies(target)
                "testImplementation"(kotlin("test"))
            }
        }
    }
}