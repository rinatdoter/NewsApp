package kg.codingtask.newstestapp.di

import android.content.Context
import androidx.room.Room
import kg.codingtask.newstestapp.BuildConfig
import kg.codingtask.newstestapp.data.db.AppDataBase
import kg.codingtask.newstestapp.data.network.NewsApi
import kg.codingtask.newstestapp.repo.NewsRepository
import kg.codingtask.newstestapp.ui.base.ArticlesVM
import kg.codingtask.newstestapp.ui.details.ArticleDetailsVM
import kg.codingtask.newstestapp.ui.main.MainVm
import kg.codingtask.newstestapp.ui.saved.SavedArticlesVM
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val vmModule = module {
    viewModel { MainVm() }
    viewModel { ArticlesVM(get()) }
    viewModel { ArticleDetailsVM(get()) }
    viewModel { SavedArticlesVM(get()) }
}

val dataModule = module {
    single { provideAppDatabase(androidApplication()) }
    single{ provideOkhttpClient()}
    single{ provideRetrofit(get())}
    factory { provideNewsApi(get()) }
    factory { NewsRepository(get(),get()) }
}

private fun provideNewsApi(retrofit: Retrofit) = retrofit.create(NewsApi::class.java)

private fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideOkhttpClient(): OkHttpClient {
    val loggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    return OkHttpClient.Builder().apply {
        connectTimeout(1, TimeUnit.MINUTES)
        readTimeout(1, TimeUnit.MINUTES)
        if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
    }.build()
}

private fun provideAppDatabase(context: Context): AppDataBase =
    Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        AppDataBase.dataBaseName
    ).fallbackToDestructiveMigration().build()