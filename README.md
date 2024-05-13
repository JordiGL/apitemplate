#TEMPLATE APP

1. Seguirem el patró de disseny data-domain-presentation + di (“Dependency Injection”)

Data: Aquesta capa gestiona tot el que està relacionat amb l’accés a les dades, com ara les operacions de base de dades o les crides a les API's.

Domain: Aquesta capa conté la lògica del model de l’aplicació. Aquí es defineixen les regles segons les quals l’aplicació opera.

Presentation: Aquesta capa gestiona la interfície d’usuari i la presentació de les dades a l’usuari.

Di: Aquesta capa conté les classes Module d'injecció de dependències relacionades amb la llibreria Koin

# Model

## **Objecte principal**
Amb les diferents propietats

# Canvi de nom del projecte

## **settings.gradle.kts**

```en-GB-oxendict
rootProject.name = "nou_nom"
```

## **com.golojodev.nou_nom**

Renombrament del directori / Clic dret -> Refactor -> Rename -> All Directories

## **NounomAPP**

```en-GB-oxendict
@HiltAndroidApp
class NounomAPP : Application()
```

## **build.gradle.kts**

```en-GB-oxendict
namespace = "com.golojodev.nou_nom"
applicationId = "com.golojodev.nou_nom"
```

## **AndroidManifest.xml**

```en-GB-oxendict
android:name=".NounomApp"
```

## **IDE - Android Studio**

Build > Clean Project > Rebuild Project

# Koin: Insercció de depèndencies

## **Llibreria**

build.gradle(Project:***)
```en-GB-oxendict
   koinAndroid = "3.4.3"
   koinAndroidxCompose = "3.4.6"
   koinCore = "3.4.3"

   koin-android = { module = "io.insert-koin:koin-android", version.ref = "koinAndroid" }
   koin-androidx-compose = { module = "io.insert-koin:koin-androidx-compose", version.ref = "koinAndroidxCompose" }
   koin-core = { module = "io.insert-koin:koin-core", version.ref = "koinCore" }
```

build.gradle(Module:***)
```en-GB-oxendict
ependencies { 
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
}
```

## **Configuració inicial**

1. Totes les APPs que utilitzen Koin han de tenir una classe «Application».

```en-GB-oxendict
class *NomApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModules)
        }
    }
}
```

2. Ara tenim que especificar al AndroidManifest la classe creada en el punt 1.

```en-GB-oxendict
***
    <application
        android:name=".*NomApplication"
        ***
```

## **Definir la vinculació entre classes amb Hilt**

Una vinculació conté la informació necessària per proporcionar instàncies d’un determinat tipus com a dependència.
Ho farem utilitzant el constructor de la classe per a indicar a koin com proporcionar les instancies.

```en-GB-oxendict
   class MainViewModel(
       private val repository: Repository
   ) : ViewModel() {
```

```en-GB-oxendict
   @Composable
   fun MainScreen(
       ***
       mainViewModel: MainViewModel = koinViewModel(),
       ***
   ) {...}
```

```en-GB-oxendict
   val mainViewModel: MainViewModel = koinViewModel()
```

## **Moduls de Koin**

En l’anterior punt, em injectat en el constructor del ViewModel el Repository. En conseqüència, haurem de crear un "Module" per indicar a Koin com instanciar un objecte de tipus Repository.
Crearem el paquet di on hi crearem l'arxiu Modules.kt

```en-GB-oxendict
   val appModules = module {
       single { ModelViewModel(get()) }
       single<ModelRepository> { ModelRepositoryImpl() }
   }
```

# Retrofit

## **Llibreria**

1. libs.versions.toml

```en-GB-oxendict
   retrofit = "2.9.0"
   retrofitSerializationConverter = "1.0.0"
   serializationJson = "1.6.2"
   coroutines = "1.7.3"
   okhttp3 = "4.11.0"

   retrofit = { module = "com.squareup.retrofit2:retrofit" , version.ref = "retrofit" }
   retrofit-serialization = { module = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter", version.ref ="retrofitSerializationConverter" }
   coroutines = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-core", version.ref = "coroutines" }
   coroutines-android = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-android" , version.ref = "coroutines" }
   serialization-json = { module = "org.jetbrains.kotlinx:kotlinx-serialization-json", version.ref = "serializationJson" }
   okhttp3 = { module = "com.squareup.okhttp3:okhttp", version.ref ="okhttp3" }
   
   networking = ["retrofit", "retrofit-serialization", "serialization-json", "coroutines", "coroutines-android", "okhttp3"]
```

2. build.gradle.kts (Project)

```en-GB-oxendict
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20" apply false
```

3. build.gradle.kts (Module)

```en-GB-oxendict
   plugins {
       ...
       id("kotlinx-serialization")
   }
   
   dependencies {
   ...
      implementation(libs.bundles.networking)
   }
```


## **Pas a pas**

1. Afegir a l’arxiu Modules.

```en-GB-oxendict
single {
   Retrofit.Builder().addConverterFactory(
      Json.asConverterFactory(contentType = "application/json".toMediaType())
   )
   .baseUrl("*url de la api*")
   .build()
}
```

2. Crear la interficie pel servei ServiceAPI.kts. Ex: https://cataas.com/api/cats?tag=cute

```en-GB-oxendict
interface ServiceAPI {
    @GET("cats")
    suspend fun fetchCats(
        @Query("tag") tag: String
    ): Response<List<Cat>>
}
```

3. Serialitzem el model respectant les claus de les dades que obtindrem de la crida a la API

```en-GB-oxendict
   @Serializable
   data class Model(
       @SerialName("_id")   
       val id: Int,
       @SerialName("title")
       val name: String,
       val tags: List<String>
   )
```

4. Afegim el modul del ServiceApi

```en-GB-oxendict
val appModules = module {
    ***
    single { get<Retrofit>().create(ServiceAPI::class.java) }
}
```

# Room / Base de dades local

## **Llibreria**

1. libs.versions.toml

```en-GB-oxendict
   room = "2.5.2"

   room-runtime = { module = "androidx.room:room-runtime" , version.ref ="room" }
   room-compiler = { module = "androidx.room:room-compiler", version.ref= "room" }
   room-ktx = { module = "androidx.room:room-ktx", version.ref = "room" }
   
   room = ["room-runtime","room-ktx"]
```

2. build.gradle.kts (Project)

```en-GB-oxendict
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
```

3. build.gradle.kts (Module)

```en-GB-oxendict
   plugins {
       ...
       id("com.google.devtools.ksp")
   }
   
   android {
      ...
      ksp {
         arg("room.schemaLocation", "$projectDir/schemas")
      }
   }
   
   dependencies {
   ...
      implementation(libs.bundles.room)
      ksp(libs.room.compiler)
   }
```

## **Pas a pas**

1. Crear la classe Entity que generara la taula a guardar a la base de dades.

```en-GB-oxendict
   @Entity( tableName = "Model")
   data class ModelEntity(
      @PrimaryKey
      val id: Int,
      val name: String,
      val tags: List<String>
   )
```

2. S'han de convertir els tipus de les propietatsa a tipus primitius. Ex: de List<String> a String 

```en-GB-oxendict
class PetsTypeConverters {
   @TypeConverter
   fun convertTagsToString(tags: List<String>): String {
      return Json.encodeToString(tags)
   }
   @TypeConverter
   fun convertStringToTags(tags: String): List<String> {
      return Json.decodeFromString(tags)
   }
}
```

3. Creem la classe DAO que defineix els metodes create, read, update per a gestionar la base de dades

```en-GB-oxendict
   @Dao
   interface ModelDao {
      @Insert(onConflict = OnConflictStrategy.REPLACE)
      suspend fun insert(modelEntity: ModelEntity)
      @Query("SELECT * FROM Model")
      fun getModels(): Flow<List<ModelEntity>>
   }
```

4. Creem la classe de la base de dades

```en-GB-oxendict
   @Database(
       entities = [ModelEntity::class],
       version = 1
   )
   @TypeConverters(PetsTypeConverters::class)
   abstract class ModelDatabase: RoomDatabase() {
       abstract fun modelDao(): ModelDao
   }
```

5. Afegim el modul de la base de dades

```en-GB-oxendict
   single {
      Room.databaseBuilder(
         androidContext(),
         ModelDatabase::class.java,
         "model-database"
      ).build()
   }
   single { get<ModelDatabase>().modelDao() }
```

6. Modifiquem el repository

```en-GB-oxendict
interface ModelRepository {
    suspend fun getModels(): Flow<List<Model>>
    suspend fun fetchRemoteModels()
}
```

```en-GB-oxendict
class ModelRepositoryImpl(
    private val serviceAPI: ServiceAPI,
    private val dispatcher: CoroutineDispatcher,
    private val modelDao: ModelDao
) : ModelRepository {
    override suspend fun getModels(): Flow<List<Model>> {
        return withContext(dispatcher) {
           modelDao.getModels()
               .map {modelsCatched ->
                   modelsCatched.map { modelEntity ->
                       Model(
                           id = modelEntity.id,
                           name = modelEntity.name,
                           tags = modelEntity.tags
                       )
                   }
               }
               .onEach {
                   if(it.isEmpty()){
                       fetchRemoteModels()
                   }
               }
        }
    }

    override suspend fun fetchRemoteModels() {
        withContext(dispatcher) {
            val response = serviceAPI.fetchData("cute")
            if (response.isSuccessful) {
                response.body()!!.map {
                    modelDao.insert(
                        ModelEntity(
                            id = it.id,
                            name = it.name,
                            tags = it.tags
                        )
                    )
                }
            }
        }
    }
}
```

6. Modifiquem els module del repository

```en-GB-oxendict
val appModules = module {
    ***
    single<ModelRepository> { ModelRepositoryImpl(get(), get(), get()) }
}
```

7. Creem l'extensio del Flow que gestionara el resultat i el convertira a NetworkResult, i modifiquem el ModelViewModel.

```en-GB-oxendict
fun <T> Flow<T>.asResult(): Flow<NetworkResult<T>> {
    return this
        .map<T, NetworkResult<T>> {
            NetworkResult.Success(it)
        }
        .catch { emit(NetworkResult.Error(it.message.toString())) }
}
```

```en-GB-oxendict
class ModelViewModel(
    private val modelRepository: ModelRepository
) : ViewModel() {
    val uiState = MutableStateFlow(UIState())

    init {
        getModels()
    }

    fun getModels(){
        uiState.value = UIState(isLoading = true)
        viewModelScope.launch {
            modelRepository.getModels().asResult().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        uiState.update {
                            it.copy(isLoading = false, models = result.data)
                        }
                    }

                    is NetworkResult.Error -> {
                        uiState.update {
                            it.copy(isLoading = false, error = result.error)
                        }
                    }
                }
            }
        }
    }
}
```

8. Proveeim el context de l'aplicacio a Koin modificant l'arxiu ApiTemplateApplication

```en-GB-oxendict
class ApiTemplateApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModules)
        }
    }
}
```
# WorkManager

WorkManager és una biblioteca de Jetpack ideal per realitzar tasques de llarga durada en segon pla.

## **Llibreria**

1. libs.versions.toml

```en-GB-oxendict
   work = "2.8.1"

   work-runtime = { module = "androidx.work:work-runtime-ktx", version.ref = "work" }
   workmanager-koin = { module = "io.insert-koin:koin-androidxworkmanager", version.ref = "koin" }
   
   worker = ["work-runtime","workmanager-koin"]
```

2. build.gradle.kts (Module)

```en-GB-oxendict   
   dependencies {
      ...
      implementation(libs.bundles.worker)
   }
```

## **Pas a pas**

1. Utilitzarem WorkManager per obtenir les dades de la API

```en-GB-oxendict
   class ModelsSyncWorker(
       appContext: Context,
       workerParams: WorkerParameters,
       private val modelRepository: ModelRepository
   ): CoroutineWorker(appContext, workerParams) {
       override suspend fun doWork(): Result {
           return try {
               modelRepository.fetchRemoteModels()
               Result.success()
           } catch (e: Exception) {
               Result.failure()
           }
       }
   }
```

2. Afegirem el modul

```en-GB-oxendict
   class ModelsSyncWorker(
       appContext: Context,
       workerParams: WorkerParameters,
       private val modelRepository: ModelRepository
   ): CoroutineWorker(appContext, workerParams) {
       override suspend fun doWork(): Result {
           return try {
               modelRepository.fetchRemoteModels()
               Result.success()
           } catch (e: Exception) {
               Result.failure()
           }
       }
   }
```

3. Crearem el metode en el MainActivity per a iniciar el worker, i l'iniciem en el onCreate

```en-GB-oxendict
    private fun startModelsSync() {
        val syncModelsWorkRequest =
            OneTimeWorkRequestBuilder<ModelsSyncWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .setRequiresBatteryNotLow(true)
                        .build()
                )
                .build()
        WorkManager.getInstance(applicationContext).
        enqueueUniqueWork(
            "ModelsSyncWorker",
            ExistingWorkPolicy.KEEP,
            syncModelsWorkRequest
        )
    }
```

3. Afegim el provider en el AndroidManifest

```en-GB-oxendict
<provider
   android:name="androidx.startup.InitializationProvider"
   android:authorities="${applicationId}.androidx-startup"
   android:exported="false"
   tools:node="merge">
   <!-- Removing WorkManager Default Initializer-->
   <meta-data
      android:name="androidx.work.WorkManagerInitializer"
      android:value="androidx.startup"
      tools:node="remove" />
</provider>
```

4. Modifiquem l'starKoin del ApiTemplateApplication

```en-GB-oxendict
class MasteringKotlinApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            ***
            workManagerFactory()
        }
    }
}
```

# Splash Screen

## **Llibreria**

```en-GB-oxendict
androidx.core:core-splashscreen:1.0.1
```

## **Pas a pas**

1. Modificar l’arxiu Theme.xml (res>values).

```en-GB-oxendict
<resources>
    <style name="Theme.*nameapp" parent="android:Theme.Material.Light.NoActionBar" />
    <style name="Theme.App.Starting" parent="Theme.SplashScreen">
        <item name="windowSplashScreenAnimatedIcon">@drawable/logo</item>
        <item name="windowSplashScreenBackground">?android:attr/textColorPrimaryInverse</item>
        <item name="postSplashScreenTheme">@style/Theme.Metabolismobasal</item>
    </style>
</resources>
```

2. Crear el ViewModel que controlara la durara del Splash Screen (presentation>viewmodel).

```en-GB-oxendict
class SplashViewModel: ViewModel() {

    private val mutableStateFlow = MutableStateFlow(true)
    val isLoading = mutableStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            mutableStateFlow.value = false
        }
    }
}
```

3. Afegir l’SplashScreen i el corresponent ViewModel creat en el MainActivity.tk (metabolismobasal).

```en-GB-oxendict
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition{splashViewModel.isLoading.value}
        val localizedContext = LanguageContextWrapper.wrap(applicationContext, Language.DEVICE)

        setContent {
            MetabolismobasalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(mainViewModel, localizedContext)
                }
            }
        }
    }
}
```

4. Modificar el manifest(app>manifests).

```en-GB-oxendict
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <application
        android:enableOnBackInvokedCallback="true"
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.App.Starting"
        tools:targetApi="tiramisu">
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:theme="@style/Theme.App.Starting">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

# Gestió dels idiomes de l'APP

## **Pas a pas**

1. Crear les classes del model.

```en-GB-oxendict
@Serializable
sealed class Language {
    abstract val name: String
    abstract val locale: String

    /**
     * Crea un [Locale] a partir de l'String que defineix l'dioma en format locale
     * @return [Locale] de l'idioma
     */
    fun toLocale() = Locale(locale)

    companion object {
        val DEVICE: Language = getDeviceLanguage()
        private fun getDeviceLanguage(): Language {
            return when(Locale.getDefault().displayLanguage){
                Catala().name -> Catala()
                Espanol().name -> Espanol()
                English().name -> English()
                else -> English()
            }
        }
    }
}

@Serializable
data class Catala(
    override val name: String = "català",
    override val locale: String = "ca"
) : Language()

@Serializable
data class Espanol(
    override val name: String = "español",
    override val locale: String = "es"
) : Language()

@Serializable
data class English(
    override val name: String = "english",
    override val locale: String = "en"
) : Language()

```

Amb la variable DEVICE obtindrem l’idioma del dispositiu, que ens permetrà establir l’idioma de l’APP (punt 3).

2. Crear la classe `ContextWrapper` que s'encarregara de la gestio de l'idioma de l'APP (presentation>util).

```en-GB-oxendict
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

class LanguageContextWrapper(baseContext: Context) : ContextWrapper(baseContext) {
    companion object {
        fun wrap(context: Context, language: Language): ContextWrapper {
            val config = Configuration(context.resources.configuration)
            val newLocale = language.toLocale()
            Locale.setDefault(newLocale)
            config.setLocale(newLocale)

            return LanguageContextWrapper(context.createConfigurationContext(config))
        }
    }
}
```

3. Afegir el `ContextWrapper` al MainActivity (metabolismobasal)

```en-GB-oxendict
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition{splashViewModel.isLoading.value}
        LanguageContextWrapper.wrap(applicationContext, Language.DEVICE)

        setContent {
            MetabolismobasalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(mainViewModel)
                }
            }
        }
    }
}
```

4. Utilitzarem stringResource(R.string.*) en els components composables per a fer la crida a l'String

```en-GB-oxendict
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = MainViewModel()
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.customShape.title
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(MaterialTheme.customSpacing.small))
            CustomText(
                text = stringResource(R.string.title),
                color = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = Modifier.padding(MaterialTheme.customSpacing.small))
        }
    }
}
```

# In-App: Proporciona una manera d’integrar les actualitzacions de l’aplicació

## **Llibreria**

```en-GB-oxendict
    implementation("com.google.android.play:app-update:2.1.0")
    implementation("com.google.android.play:app-update-ktx:2.1.0")
```

## **Pas a pas**

1. Crear el Handler; aquest comprova si hi ha una actualització disponible al GooglePlayStore. En cas afirmatiu, pregunta a l'usuari si vol actualitzar l'App

```en-GB-oxendict
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleCoroutineScope
import com.golojodev.metabolismobasal.R
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

object AppUpdateHandler {
    private lateinit var activity: ComponentActivity
    private lateinit var lifecycleScope: LifecycleCoroutineScope
    private lateinit var appUpdateManager: AppUpdateManager
    private val updateType = AppUpdateType.FLEXIBLE
    private var toast: Toast? = null
    private val installStateUpdatedListener = InstallStateUpdatedListener { state ->
        when (state.installStatus()) {
            InstallStatus.DOWNLOADED -> showDownloadedToast()
            InstallStatus.DOWNLOADING -> showDownloadingToast()
            else -> {}
        }
    }

    fun init(activity: ComponentActivity, lifecycleScope: LifecycleCoroutineScope) {
        this.activity = activity
        this.lifecycleScope = lifecycleScope
        setupAppUpdateManager()
    }

    private fun setupAppUpdateManager() {
        appUpdateManager = AppUpdateManagerFactory.create(activity.applicationContext)
        appUpdateManager.registerListener(installStateUpdatedListener)
        checkForUpdate()
    }
    fun checkForUpdate(notifyIfNoPendingUpdate: Boolean = false) {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    activity,
                    AppUpdateOptions.defaultOptions(updateType),
                    123
                )
            } else {
                if(notifyIfNoPendingUpdate){
                    cancelToast()
                    toast = Toast.makeText(
                        activity.applicationContext,
                        activity.applicationContext.getString(R.string.no_pending_updates),
                        Toast.LENGTH_SHORT
                    )
                    toast?.show()
                }
            }
        }
    }

    private fun showDownloadedToast() {
        cancelToast()
        toast = Toast.makeText(
            activity.applicationContext,
            activity.applicationContext.getString(R.string.restarting_app),
            Toast.LENGTH_LONG
        )
        toast?.show()
        lifecycleScope.launch {
            delay(5.seconds)
            appUpdateManager.completeUpdate()
        }
    }

    private fun showDownloadingToast() {
        if (toast == null) {
            toast = Toast.makeText(
                activity.applicationContext,
                activity.applicationContext.getString(R.string.downloading_update),
                Toast.LENGTH_SHORT
            )
            toast?.show()
        }
    }

    private fun cancelToast() {
        toast?.cancel()
    }

    fun checkIfUpdateDownloaded() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { state ->
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                showDownloadedToast()
            }
        }
    }

    fun unregisterListener() {
        appUpdateManager.unregisterListener(installStateUpdatedListener)
    }
}
```

2. Modificar el MainActivity; inicialitzem el handler amb el mètode Init a l'onCreate, i sobreescrivim els mètodes onResume i onDestroy

```en-GB-oxendict
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this)
        loadInterstitial(this)

        splashScreen.setKeepOnScreenCondition{splashViewModel.isLoading.value}
        LanguageContextWrapper.wrap(applicationContext, Language.DEVICE)
        AppUpdateHandler.init(this, lifecycleScope)

        setContent {
            MetabolismobasalTheme {
                val gradient = Brush.verticalGradient(
                    colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.background)
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().background(brush = gradient),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        CustomNavHost(mainViewModel = mainViewModel)
                        AdmobBanner(modifier = Modifier.align(Alignment.BottomCenter))
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        AppUpdateHandler.checkIfUpdateDownloaded()
    }

    override fun onDestroy() {
        AppUpdateHandler.unregisterListener()
        removeInterstitial()
        super.onDestroy()
    }
}
```

# AdMob: Publicitat a l'App

## **Llibreria**

```en-GB-oxendict
    implementation("com.google.android.gms:play-services-ads:22.6.0")
```

## **Pas a pas**

1. Crear el Manager; aquesta classe gestiona tot allò relacionat amb la crida dels diferents components que utilitzarem

```en-GB-oxendict
object AdmobManager {
    var mInterstitialAd: InterstitialAd? = null

    fun loadInterstitial(context: Context) {
        InterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/1033173712", //Change this with your own AdUnitID!
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            }
        )
    }

    fun showInterstitial(context: Context, onAdDismissed: () -> Unit) {
        val activity = context.findActivity()

        if (mInterstitialAd != null && activity != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    mInterstitialAd = null
                }

                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null

                    loadInterstitial(context)
                    onAdDismissed()
                }
            }
            mInterstitialAd?.show(activity)
        }
    }

    fun removeInterstitial() {
        mInterstitialAd?.fullScreenContentCallback = null
        mInterstitialAd = null
    }

    private fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }

    @Composable
    fun Banner(
        modifier: Modifier = Modifier,
        padding: Dp = MaterialTheme.customSpacing.small
    ) {
        AndroidView(
            modifier = modifier.fillMaxWidth().padding(bottom = padding),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    //Utilitzar sempre anuncis de prova amb: ca-app-pub-3940256099942544/6300978111
                    adUnitId = "ca-app-pub-3940256099942544/6300978111"
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}
```

2. Inicialització de la llibreria al MainActivity: MobileAds.initialize(this)

```en-GB-oxendict
class MainActivity : ComponentActivity() {
    ***
    
    override fun onCreate(savedInstanceState: Bundle?) {
        ***
        super.onCreate(savedInstanceState)
        
        MobileAds.initialize(this)
        ***
    }
    ***
}
```

## **Intestial**

1. Configuració d'aquest en el MainActivity

```en-GB-oxendict
class MainActivity : ComponentActivity() {
    ***

    override fun onCreate(savedInstanceState: Bundle?) {
        ***
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this)
        AdmobManager.loadInterstitial(this)
        ***
    }
    
    ***

    override fun onDestroy() {
        ***
        AdmobManager.removeInterstitial()
        super.onDestroy()
    }
}
```

2. Crida de l'Interstitial

```en-GB-oxendict
    AdmobManager.showInterstitial(context) {
        //OnAdDissmised
    }
```

Exemple:

```en-GB-oxendict
    Random.nextInt(1, 7).takeIf { it == 4 }?.let {
        AdmobManager.showInterstitial(context) {
            mainViewModel.setResult()
        }
    } ?: mainViewModel.setResult()
```

## **Banner**

1. Col·locació del Banner

```en-GB-oxendict
    AdmobManager.Banner()
```

Exemple:

```en-GB-oxendict
    Box(
        modifier = Modifier.fillMaxSize().background(brush = gradient),
        contentAlignment = Alignment.TopCenter
    ) {
        CustomNavHost(mainViewModel = mainViewModel)
        AdmobManager.Banner(modifier = Modifier.align(Alignment.BottomCenter))
    }
```

# PermissionManager: Gestiona la sol·licitud i comprobació de permisos

## **Pas a pas**

1. Afegir el permís de notificacio en l'AndroidManifest.xml

    ```en-GB-oxendict
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    ```
   
2. Crear el Handler

    ```en-GB-oxendict
    /**
     * Gestiona la sol·licitud i comprobació de permisos.
     */
    object PermissionManager {
    
        /**
         * Comprova i sol·licita el permís per a mostrar notificacions, si és necessari.
         *
         * @param context Context de l'aplicació
         */
        fun requestNotificationPermission(context: Context) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
                if(!hasPermission){
                    context.findActivity()?.let {
                        ActivityCompat.requestPermissions(
                            it,
                            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                            0
                        )
                    }
                }
            }
        }
    }
    ```
   
3. Fer la petició

    ```en-GB-oxendict
    PermissionManager.requestNotificationPermission(context)
    ```

# NotificationHandler: Gestiona la creació i la visualització de notificacions

## **Pas a pas**

1. Crear el servei de notificacions i afegir-lo en el AndroidManifest.xml

    ```en-GB-oxendict
    <application>
        ...
        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="com.golojodev.maria"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/paths" />
        </provider>
    </application>
    ```

2. Crear el Handler

    ```en-GB-oxendict
    /**
     * Gestiona la creació i la visualització de notificacions.
     */
    class NotificationHandler(
        private val context: Context,
        private val notificationManager: NotificationManager
    ) {
    
        /**
         * Mostra una notificació amb el títol i el cos especificats.
         *
         * @param channel Canal de notificació a utilitzar.
         * @param title Títol de la notificació.
         * @param body Cos de la notificació.
         */
        fun showNotification(
            channel: NotificationChannel,
            title: String,
            body: String,
            icon: Int
        ) {
            notificationManager.createNotificationChannel(channel)
    
            val notification = NotificationCompat.Builder(context, channel.id)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(icon)
                .setAutoCancel(true)
                .build()
    
            notificationManager.notify(0, notification)
        }
    }
    ```

3. Crear la notificació dintre del servei de notificacions degut a que utilitzarem el context d'aquest (this)

    ```en-GB-oxendict
    NotificationHandler(
        context = this,
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    ).showNotification(
        channel = NotificationChannel(
            "FCM", "FCM notification",
            NotificationManager.IMPORTANCE_DEFAULT
        ),
        title = title,
        body = body,
        icon = R.drawable.icon_alert
    )
    ```

# NetworkManager: Proporciona mètodes per comprovar la connexió a internet del dispositiu.

## **Pas a pas**

1. Afegir al permís a l'AndroidManifest.xml

   ```en-GB-oxendict
    <uses-permission android:name="android.permission.INTERNET" />
   ```
2. Crear el manager

   ```en-GB-oxendict
   /**
    * Proporciona mètodes per comprovar la connexió a internet del dispositiu.
    */
   object NetworkManager {
       /**
        * Comprova si el dispositiu està connectat a internet.
        *
        * @param context Context de l'aplicació.
        * @return True si el dispositiu està connectat a internet, False altrament.
        */
       fun isConnectedToInternet(context: Context): Boolean {
           val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
           val activeNetwork = connectivityManager.activeNetworkInfo
           return activeNetwork != null && activeNetwork.isConnectedOrConnecting
       }
   }
   ```

# NetworkMonitor: Proporciona funcionalitats per monitoritzar l'estat de la connexió a internet del dispositiu en un interval de temps determinat

## **Pas a pas**

1. Crear el monitor

   ```en-GB-oxendict
   /**
    * Proporciona funcionalitats per monitoritzar l'estat de la connexió a internet del dispositiu
    * en un interval de temps determinat.
    */
   class NetworkMonitor {
       private var monitoringJob: Job? = null
       private val _isConnected = MutableStateFlow(false)
       val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()
   
       /**
        * Inicia la monitorització de la connexió a internet.
        *
        * @param interval Interval de temps en mil·lisegons entre cada comprovació.
        */
       fun startMonitoring(context: Context, interval: Long) {
           monitoringJob = CoroutineScope(Dispatchers.IO).launch {
               while (isActive) {
                   val isConnected = NetworkManager.isConnectedToInternet(context)
                   _isConnected.value = isConnected
                   delay(interval)
               }
           }
       }
   
       /**
        * Atura la monitorització de la connexió a internet.
        */
       fun stopMonitoring() {
           monitoringJob?.cancel()
       }
   }
   ```

2. Iniciar la monitorització

   ```en-GB-oxendict
   val networkMonitor = NetworkMonitor()
   networkMonitor.startMonitoring(this, 2000) //Interval de 2 segons
   ```

# IntentManager: Facilita la creació i gestió d'Intents a Android

## **Pas a pas**

1. Crear el Manager

   ```en-GB-oxendict
   /**
    * Facilita la creació i gestió d'Intents a Android.
    */
   object IntentManager {
   
       /**
        * Crea un `Intent` amb els paràmetres especificats.
        *
        * @param action L'acció que l'Intent ha d'executar.
        * @param data Les dades que l'Intent ha de processar.
        * @param type El tipus de dades que l'Intent ha de processar.
        * @param setPackage El paquet de l'aplicació que ha d'obrir l'Intent.
        * @param extras Un conjunt de dades addicionals per a l'Intent.
        *
        * @return Un `Intent` configurat amb els paràmetres especificats.
        */
       private fun createIntent(
           action: String,
           data: Uri? = null,
           type: String? = null,
           setPackage: String? = null,
           extras: Bundle? = null
       ): Intent {
           return Intent(action).apply {
               data?.let { this.data = it }
               extras?.let { this.putExtras(it) }
               type?.let { this.type = it }
               setPackage?.let { this.setPackage(it) }
               addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
           }
       }
   
       /**
        * Genera un `Intent` per enviar un correu electrònic quan s'executa.
        * Està configurat per enviar un correu electrònic a l'adreça especificada amb l'assumpte del correu electrònic establert.
        *
        * @exception Exception Si no hi ha cap aplicació instal·lada en el dispositiu que pugui gestionar aquest `Intent`
        */
       fun createEmail(context: Context, emails: Array<String>, subject: String) {
           val intent = createIntent(
               action = Intent.ACTION_SEND,
               type = context.getString(R.string.type_text_plain),
               data = null,
               extras = Bundle().apply {
                   putStringArray(Intent.EXTRA_EMAIL, emails)
                   putString(Intent.EXTRA_SUBJECT, subject)
               }
           )
           context.handleIntent(intent, "createEmail")
       }
   
       /**
        * Genera un `Intent` per obrir el perfil de Google Play quan s'executa.
        * Està configurat per obrir el perfil de Google Play amb l'ID especificat.
        *
        * @exception Exception Si no hi ha cap aplicació instal·lada en el dispositiu que pugui gestionar aquest `Intent`
        */
       fun openGooglePlayProfile(context: Context) {
           val intent = createIntent(
               action = Intent.ACTION_VIEW,
               data = Uri.parse("https://play.google.com/store/apps/dev?id=7061416679954621830"),
               setPackage = "com.android.vending",
           )
           context.handleIntent(intent, "openGooglePlayProfile")
       }
   }
   ```

2. Inicialitzar un intent per a crear un email

   ```en-GB-oxendict
    IntentManager.createEmail(
        context = context,
        emails = arrayOf(applicationContext.getString(R.string.golojodev_gmail_com)),
        subject = applicationContext.getString(R.string.app_name)
    )
   ```

# ContentSharingManager: Gestiona l'operació de compartir imatges

## **Llibreria**

```en-GB-oxendict
    implementation("dev.shreyaspatil:capturable:1.0.3")
```

## **Pas a pas**

1. Crear el Manager; aquesta classe gestiona tot allò relacionat amb la crida dels diferents components que utilitzarem

   ```en-GB-oxendict
   /**
    * Gestiona l'operació de compartir imatges.
    *
    * @param context El context de l'aplicació actual.
    */
   class ContentSharingManager(private val context: Context) {
   
       /**
        * Comparteix una imatge a través d'una aplicació externa.
        *
        * @param bitmap La imatge que es vol compartir.
        */
       fun shareImage(bitmap: Bitmap) {
           val shareFile: suspend (File) -> Unit = { file ->
               val imageUri = FileProvider.getUriForFile(
                   context,
                   "com.golojodev.template",
                   file
               )
               val shareIntent: Intent = Intent().apply {
                   action = Intent.ACTION_SEND
                   putExtra(Intent.EXTRA_STREAM, imageUri)
                   type = context.getString(R.string.type_image)
                   flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
               }
               context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_result)))
           }
   
           CoroutineScope(Dispatchers.IO).launch {
               val file = createTemporaryImageFile(bitmap)
               shareFile(file)
           }
       }
   
       /**
        * Crea un fitxer temporal on s'emmagatzemarà la imatge abans de compartir-la.
        *
        * @param bitmap La imatge que es vol emmagatzemar.
        *
        * @return Un objecte `File` que representa el fitxer temporal creat.
        */
       private suspend fun createTemporaryImageFile(bitmap: Bitmap): File = withContext(Dispatchers.IO) {
           val path = context.cacheDir.toString() + "/shared_image.jpg"
           val file = File(path)
           val out: OutputStream = FileOutputStream(file)
           bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
           out.flush()
           out.close()
           file
       }
   }
   ````
   
2. Capturar Composable per a compartir-lo

   ```en-GB-oxendict
   val contentSharingManager = ContentSharingManager(context)
   val captureController = rememberCaptureController()
   
   CustomTextButton(text = "Capture and share", contentColor = Color.White){
      captureController.capture()
   }
   Capturable(
      controller = captureController,
      onCaptured = { imageBitmap, error ->
          val bitmap: Bitmap? = imageBitmap?.asAndroidBitmap()
   
          bitmap?.let { contentSharingManager.shareImage(it) }
          error?.let { error.message?.let { e -> Log.i("Capturable", e) } }
      }
   ) {
      CustomText(
          modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background),
          textAlign = TextAlign.Center,
          text = "Capturable text"
      )
   }
   ```
   
# Tests

## **Llibreria**

```en-GB-oxendict
    testImplementation(kotlin("test"))
    testImplementation("io.strikt:strikt-core:0.34.0")
```

## **Exemple**

```en-GB-oxendict
    fun calculateBMR(){
        repeat(100) {
            val height = 150.00
            val weight = 50.00
            val age = 25
        
            expectThat(
                RevisedHarrisBenedict().calculateCalories(Female(), Unspecified(), weight, height, age).roundToInt()
            ).isEqualTo(
                1266
            )
        }
    }
```
