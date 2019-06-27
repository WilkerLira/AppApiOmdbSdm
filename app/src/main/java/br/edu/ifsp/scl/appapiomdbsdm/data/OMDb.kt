package br.edu.ifsp.scl.appomdbapisdm.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Resposta(
    val totalResults: Int,
    val Response: Boolean,
    var Search: MutableList<Search?>? = mutableListOf()
):Parcelable

@Parcelize
data class Search(
    val Title: String
    , val Year: String
    , val imdbID: String
    , val Type: String
    , val Poster: String
):Parcelable

@Parcelize
data class OMDb(
    val Title: String
    , val Year: String
    , val Rated: String
    , val Released: String
    , val Runtime: String
    , val Genre: String
    , val Director: String
    , val Writer: String
    , val Actors: String
    , val Plot: String
    , val Language: String
    , val Country: String
    , val Poster: String
    , var Ratings: List<Ratings?>? = listOf()
    , val Awards: String
    , val Metascore: String
    , val imdbRating: String
    , val imdbVotes: String
    , val imdbID: String
    , val Type: String
    , val DVD: String
    , val BoxOffice: String
    , val Production: String
    , val Website: String
    , val Response: String
):Parcelable

@Parcelize
data class Ratings(
    val Source: String
    , val Value: String
):Parcelable