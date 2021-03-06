package com.rockthevote.grommet.data.api.model;


import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.rockthevote.grommet.data.db.model.RockyRequest;
import com.rockthevote.grommet.util.Dates;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

import static com.rockthevote.grommet.data.db.model.Name.Gender;
import static com.rockthevote.grommet.data.db.model.Name.Prefix;

@AutoValue
public abstract class ApiVoterRegistration {

    @Json(name = "registration_helper")
    @Nullable
    abstract ApiRegistrationHelper registrationHelper();

    @Json(name = "date_of_birth")
    abstract String dateOfBirth();

    @Json(name = "mailing_address")
    @Nullable
    abstract ApiAddress mailingAddress();

    @Json(name = "previous_registration_address")
    @Nullable
    abstract ApiAddress previousRegistrationAddress();

    @Json(name = "registration_address")
    abstract ApiAddress registrationAddress();

    @Json(name = "registration_address_is_mailing_address")
    abstract boolean regIsMail();

    public abstract ApiName name();

    @Json(name = "previous_name")
    @Nullable
    abstract ApiName previousName();

    abstract String gender();

    abstract String race();

    abstract String party();

    @Json(name = "voter_classifications")
    abstract List<ApiVoterClassification> voterClassifications();

    abstract ApiSignature signature();

    @Json(name = "voter_ids")
    abstract List<ApiVoterId> voterIds();

    @Json(name = "contact_methods")
    abstract List<ApiContactMethod> contactMethods();

    @Json(name = "additional_info")
    abstract List<ApiAdditionalInfo> additionalInfo();


    public static JsonAdapter<ApiVoterRegistration> jsonAdapter(Moshi moshi) {
        return new AutoValue_ApiVoterRegistration.MoshiJsonAdapter(moshi);
    }

    static Builder builder() {
        return new AutoValue_ApiVoterRegistration.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder registrationHelper(ApiRegistrationHelper registrationHelper);

        abstract Builder dateOfBirth(String value);

        abstract Builder mailingAddress(ApiAddress value);

        abstract Builder previousRegistrationAddress(ApiAddress value);

        abstract Builder registrationAddress(ApiAddress value);

        abstract Builder regIsMail(boolean value);

        abstract Builder name(ApiName value);

        abstract Builder previousName(ApiName value);

        abstract Builder gender(String value);

        abstract Builder race(String value);

        abstract Builder party(String value);

        abstract Builder voterClassifications(List<ApiVoterClassification> values);

        abstract Builder signature(ApiSignature value);

        abstract Builder voterIds(List<ApiVoterId> values);

        abstract Builder contactMethods(List<ApiContactMethod> values);

        abstract Builder additionalInfo(List<ApiAdditionalInfo> values);

        abstract ApiVoterRegistration build();
    }

    public static ApiVoterRegistration fromDb(RockyRequest rockyRequest,
                                              ApiAddress mailingAddress,
                                              ApiAddress previousRegistrationAddress,
                                              ApiAddress registrationAddress,
                                              ApiName name,
                                              ApiName previousName,
                                              List<ApiVoterClassification> voterClassifications,
                                              ApiSignature signature,
                                              List<ApiVoterId> voterIds,
                                              List<ApiContactMethod> contactMethods,
                                              List<ApiAdditionalInfo> additionalInfo,
                                              ApiRegistrationHelper registrationHelper) {

        String party;
        switch (rockyRequest.party()) {
            case OTHER_PARTY:
                party = rockyRequest.otherParty();
                break;
            default:
                party = rockyRequest.party().toString();
                break;
        }

        Builder builder = builder();

        builder
                .dateOfBirth(Dates.formatAsISO8601_ShortDate(rockyRequest.dateOfBirth()))
                .mailingAddress(mailingAddress)
                .previousRegistrationAddress(previousRegistrationAddress)
                .registrationAddress(registrationAddress)
                .regIsMail(!rockyRequest.hasMailingAddress())
                .name(name)
                .previousName(previousName)
                .gender(Gender.fromPrefix(Prefix.fromString(name.titlePrefix())).toString())
                .party(party)
                .voterClassifications(voterClassifications)
                .signature(signature)
                .voterIds(voterIds)
                .contactMethods(contactMethods)
                .additionalInfo(additionalInfo)
                .registrationHelper(registrationHelper)
        ;

        if (null != rockyRequest.race()) {
            builder.race(rockyRequest.race().toString());
        }
        return builder.build();
    }
}
