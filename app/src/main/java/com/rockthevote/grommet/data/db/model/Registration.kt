package com.rockthevote.grommet.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Entity for registrations in the database. Autogenerates a [uid], taking in a
 * [registrationDate] in millis, [registrantName] and [registrantEmail] for identification,
 * and a full json string as [registrationData]
 */
@Entity(tableName = "registration",
        foreignKeys = [ForeignKey(
                entity = Session::class,
                parentColumns = ["session_id"],
                childColumns = ["session_id"],
                onDelete = ForeignKey.CASCADE
        )]
)
data class Registration(
        @PrimaryKey(autoGenerate = true)
        val registrationId: Long = 0,
        @ColumnInfo(name = "session_id")
        val sessionId: Long = 0, // todo remove default
        @ColumnInfo(name = "registration_date")
        val registrationDate: Long,
        @ColumnInfo(name = "registrant_name")
        val registrantName: String,
        @ColumnInfo(name = "registrant_email")
        val registrantEmail: String,
        @ColumnInfo(name = "registration_data")
        val registrationData: String
)