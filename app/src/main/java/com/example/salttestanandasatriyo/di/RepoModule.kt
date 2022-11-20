package  com.example.salttestanandasatriyo.di


import android.content.Context
import android.content.SharedPreferences
import com.example.salttestanandasatriyo.common.*
import com.example.salttestanandasatriyo.data.*
import com.example.salttestanandasatriyo.data.sources.homeCahedData.*
import com.example.salttestanandasatriyo.data.sources.remoteApi.*
import com.example.salttestanandasatriyo.domain.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {



    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) as SharedPreferences


    @Singleton
    @Provides
    fun provideSharedPreferenceEditor(sharedPreferences: SharedPreferences)=
         sharedPreferences.edit() as SharedPreferences.Editor


    @Singleton
    @Provides
    fun sharedPrefHelper(sharedPreferences: SharedPreferences,editor: SharedPreferences.Editor)=
        SharedPrefHelperImpl(sharedPreferences,editor)  as SharedPrefHelper



    @Provides
    @Singleton
    fun provideNewsRepository(iOfflineDataSource: OfflineDataSource, iOnlineDataSource: OnlineDataSource,
                              sharedPrefHelper: SharedPrefHelper,iNetworkAwareHandler: NetworkAwareHandler)
            = NewsRepositoryImpl(iOfflineDataSource,iOnlineDataSource,sharedPrefHelper,iNetworkAwareHandler) as NewsRepository

    @Provides
    @Singleton
    fun provideIOfflineDataSource (homeDao: HomeNewsDao)
            = OfflineDataSourceImpl(homeDao) as OfflineDataSource


    @Provides
    @Singleton
    fun provideIOnlineDataSource( service: ApiHelper)
            = OnlineDataSourceImpl(service) as OnlineDataSource

    @Provides
    @Singleton
    fun provideINetworkAwareHandler( @ApplicationContext context: Context)
            =NetworkHandlerImpl(context) as NetworkAwareHandler


    @Provides
    @Singleton
    fun provideIApiHelper( apiService: ApiService)
            =ApiHelperImpl(apiService) as ApiHelper

}


