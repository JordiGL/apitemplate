package com.golojodev.library.ad

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.golojodev.library.utils.findActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable

/**
 * Gestiona els anuncis intersticials i de bàner de l'aplicació.
 */
object AdmobManager {

    private const val INTERSTITIAL_TEST_ID = "ca-app-pub-3940256099942544/1033173712"
    private const val INTERSTITIAL_ID = "ca-app-pub-3738136916548558/7119105719"
    private const val BANNER_TEST_ID = "ca-app-pub-3940256099942544/6300978111"
    private const val BANNER_ID = "ca-app-pub-3738136916548558/9745269055"
    private const val MANIFEST_ID = "ca-app-pub-3738136916548558~1933060355"
    private const val MANIFEST_TEST_ID = "ca-app-pub-3940256099942544~3347511713"
    private var disposable: Disposable? = null
    private fun createAdRequest(): AdRequest = AdRequest.Builder().build()

    /**
     * Funció per carregar un InterstitialAd de forma asíncrona utilitzant RxJava Single.
     *
     * Aquesta funció crea un objecte Single que emet un objecte InterstitialAd en cas d'èxit
     * o un error en cas de falla.
     *
     * @param context El context de l'aplicació.
     * @param adId L'ID de l'anunci interstitial. Per defecte, utilitza un ID de prova.
     * @return Un objecte Single que emet un InterstitialAd o un error.
     */
    fun loadInterstitial(
        context: Context,
        adId: String = INTERSTITIAL_TEST_ID
    ): Single<InterstitialAd> {
        return Single.create { emitter ->
            InterstitialAd
                .load(
                    context,
                    adId,
                    createAdRequest(),
                    object : InterstitialAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            emitter.onError(Exception("Error al carregar l'anunci interstitial"))
                        }

                        override fun onAdLoaded(interstitialAd: InterstitialAd) {
                            emitter.onSuccess(interstitialAd)
                        }
                    }
                )
        }
    }

    /**
     * Funció per mostrar un InterstitialAd carregat prèviament.
     *
     * Aquesta funció utilitza la subscripció RxJava per gestionar l'emissió de l'objecte
     * InterstitialAd i mostrar-lo a la pantalla completa.
     *
     * @param context El context de l'aplicació.
     * @param onAdDismissed Funció callback que s'executa quan l'anunci es tanca.
     */
    fun showInterstitial(context: Context, onAdDismissed: () -> Unit) {
        disposable = loadInterstitial(context)
            .subscribe(
                { interstitialAd ->
                    val activity = context.findActivity() ?: return@subscribe
                    with(interstitialAd) {
                        fullScreenContentCallback = object : FullScreenContentCallback() {
                            override fun onAdFailedToShowFullScreenContent(e: AdError) {
                                Log.e("AdMob", "Failed to show interstitial ad: ${e.message}")
                                removeInterstitial()
                            }

                            override fun onAdDismissedFullScreenContent() {
                                onAdDismissed()
                                removeInterstitial()
                            }
                        }
                        setImmersiveMode(true) // S'adapta al canvi de posicio de pantalla.
                        show(activity)
                    }
                },
                { error ->
                    Log.e("AdMob", "Failed to show interstitial ad: ${error.message}")
                }
            )
    }

    /**
     * Funció per mostrar un InterstitialAd carregat prèviament de forma randomizada.
     *
     * Aquesta funció utilitza la subscripció RxJava per gestionar l'emissió de l'objecte
     * InterstitialAd i mostrar-lo a la pantalla completa.
     *
     * @param context El context de l'aplicació.
     * @param randomizer El randomizador que determinara si es mostra o no l'interstitial
     * @param onAdDismissed Funció callback que s'executa quan l'anunci es tanca.
     */
    fun showRandomizedInterstitial(
        context: Context,
        randomizer: Boolean = false,
        onAdDismissed: () -> Unit
    ) {
        if (randomizer) {
            disposable = loadInterstitial(context)
                .subscribe(
                    { interstitialAd ->
                        val activity = context.findActivity() ?: return@subscribe
                        with(interstitialAd) {
                            fullScreenContentCallback = object : FullScreenContentCallback() {
                                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                                    Log.e("AdMob", "Failed to show interstitial ad: ${e.message}")
                                    removeInterstitial()
                                }

                                override fun onAdDismissedFullScreenContent() {
                                    onAdDismissed()
                                    removeInterstitial()
                                }
                            }
                            setImmersiveMode(true) // S'adapta al canvi de posicio de pantalla.
                            show(activity)
                        }
                    },
                    { error ->
                        Log.e("AdMob", "Failed to show interstitial ad: ${error.message}")
                    }
                )
        } else {
            onAdDismissed()
        }
    }

    /**
     * Elimina la subscripció a l'objecte `InterstitialAd` i el descarrega de la memòria.
     */
    private fun removeInterstitial() {
        disposable?.dispose()
    }

    /**
     * Composable que mostra un banner d'anuncis a la part inferior de la pantalla.
     *
     * @param modifier Modificadors per a l'estil del banner.
     * @param padding Padding inferior del banner.
     * @param size Mida del banner.
     * @param adId ID de l'anunci del banner. Per defecte, utilitza un ID de prova.
     */
    @Composable
    fun Banner(
        modifier: Modifier = Modifier,
        padding: Dp = 8.dp,
        size: AdSize = AdSize.BANNER,
        adId: String = BANNER_TEST_ID
    ) {
        AndroidView(
            modifier = modifier.fillMaxWidth().padding(bottom = padding),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(size)
                    adUnitId = adId
                    loadAd(createAdRequest())
                }
            }
        )
    }
}