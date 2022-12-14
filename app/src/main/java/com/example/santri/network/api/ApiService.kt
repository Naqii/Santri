package com.example.santri.network.api

import com.example.santri.network.model.SantriResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //GET ALL
    @GET("data")
    fun getSantri(): Call<SantriResponse>

    //GET BY NAME
    @GET("data/{name}")
    fun getSrcName (
        @Path("name")
        name: String
    ): Call<SantriResponse>

    //GET BY ID
    @GET("dataa/{id}")
    fun getSrcSantri (
        @Path("id")
        id: String
    ): Call<SantriResponse>

    //POST/ADD Data
    @FormUrlEncoded
    @POST("data")
    fun getCreateSantri(
        @Field("nis") nis: String?,
        @Field("name") name: String?,
        @Field("telp") telp: String?,
        @Field("address") address: String?,
        @Field("city") city: String?,
        @Field("province") province: String?,
        @Field("birth") birth: String?,
        @Field("email") email: String?,
        @Field("nilai_sikap") nilai_sikap: String?,
        @Field("nilai_materi") nilai_materi: String?,
        @Field("nilai_bacaan") nilai_bacaan: String?,
        @Field("nilai_hafalan") nilai_hafalan: String?,
        @Field("presensi_hadir") presensi_hadir: String?,
        @Field("presensi_izin") presensi_izin: String?,
        @Field("presensi_alfa") presensi_alfa: String?,
        @Field("presensi_keterangan") presensi_keterangan: String?,
        @Field("kampus_univ") kampus_univ: String?,
        @Field("kampus_progdi") kampus_progdi: String?,
        @Field("kampus_jurusan") kampus_jurusan: String?,
        @Field("kampus_gelar") kampus_gelar: String?,
    ): Call<SantriResponse>

    //POST/UPDATE
    @FormUrlEncoded
    @PATCH("data/{id}")
    fun getUpdateSantri(
        //For Url
        @Path("id") id: String,
        //Data Field Update
        @Field("id") idField: String,
        @Field("nis") nis: String?,
        @Field("name") name: String?,
        @Field("telp") telp: String?,
        @Field("address") address: String?,
        @Field("city") city: String?,
        @Field("province") province: String?,
        @Field("birth") birth: String?,
        @Field("email") email: String?,
        @Field("nilai_sikap") nilai_sikap: String?,
        @Field("nilai_materi") nilai_materi: String?,
        @Field("nilai_bacaan") nilai_bacaan: String?,
        @Field("nilai_hafalan") nilai_hafalan: String?,
        @Field("presensi_hadir") presensi_hadir: String?,
        @Field("presensi_izin") presensi_izin: String?,
        @Field("presensi_alfa") presensi_alfa: String?,
        @Field("presensi_keterangan") presensi_keterangan: String?,
        @Field("kampus_univ") kampus_univ: String?,
        @Field("kampus_progdi") kampus_progdi: String?,
        @Field("kampus_jurusan") kampus_jurusan: String?,
        @Field("kampus_gelar") kampus_gelar: String?,
    ): Call<SantriResponse>

    //DELETE
    @DELETE("data/{id}")
    fun getDeleteSantri(
        //For Url
        @Path("id") id: String
    ): Call<SantriResponse>
}