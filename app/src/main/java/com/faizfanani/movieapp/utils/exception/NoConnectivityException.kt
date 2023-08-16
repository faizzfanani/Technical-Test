package com.faizfanani.movieapp.utils.exception

import com.faizfanani.movieapp.utils.Constants
import java.io.IOException

/**
 * Created by Moh.Faiz Fanani on 17/03/2023
 */
class NoConnectivityException : IOException() {
    override val message: String = Constants.ERROR_NO_CONNECTION
}