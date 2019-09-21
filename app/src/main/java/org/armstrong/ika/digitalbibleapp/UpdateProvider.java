package org.armstrong.ika.digitalbibleapp;

import android.content.Context;

import org.armstrong.ika.digitalbibleapp.VerKeyDb.VersionEntities;
import org.armstrong.ika.digitalbibleapp.VerKeyDb.VersionRepository;

import java.util.List;

import static org.armstrong.ika.digitalbibleapp.Constants.AFR53_DATABASE_NAME;
import static org.armstrong.ika.digitalbibleapp.Constants.AFR83_DATABASE_NAME;
import static org.armstrong.ika.digitalbibleapp.Constants.CLEM_DATABASE_NAME;
import static org.armstrong.ika.digitalbibleapp.Constants.CPDV_DATABASE_NAME;
import static org.armstrong.ika.digitalbibleapp.Constants.DN1933_DATABASE_NAME;
import static org.armstrong.ika.digitalbibleapp.Constants.KJV_DATABASE_NAME;
import static org.armstrong.ika.digitalbibleapp.Constants.LANGUAGE_KEY_DATABASE_NAME;
import static org.armstrong.ika.digitalbibleapp.Constants.NOVA_DATABASE_NAME;
import static org.armstrong.ika.digitalbibleapp.Constants.UKJV_DATABASE_NAME;
import static org.armstrong.ika.digitalbibleapp.Constants.VERSION_KEY_DATABASE_NAME;
import static org.armstrong.ika.digitalbibleapp.Constants.WEBBE_DATABASE_NAME;

public class UpdateProvider {

    Context context;

    protected VersionRepository versionRepository;

    List<VersionEntities> versionEntities;

    public UpdateProvider(Context context) {
        this.context = context;

        // install language key
        new GeneralCopier(context, LANGUAGE_KEY_DATABASE_NAME).initializeCopier(4);

        // install version key
        new GeneralCopier(context, VERSION_KEY_DATABASE_NAME).initializeCopier(7);
    }

    public UpdateProvider initialize(VersionRepository versionRepository) {

        versionEntities = versionRepository.listActiveVersions();

        for (int i = 0; i < versionEntities.size(); i++) {

            switch (versionEntities.get(i).getNumber()) {

                case 1:
                    new GeneralCopier(context, KJV_DATABASE_NAME).initializeCopier(3);
                    break;
                case 2:
                    new GeneralCopier(context, CLEM_DATABASE_NAME).initializeCopier(3);
                    break;
                case 3:
                    new GeneralCopier(context, CPDV_DATABASE_NAME).initializeCopier(3);
                    break;
                case 4:
                    new GeneralCopier(context, NOVA_DATABASE_NAME).initializeCopier(3);
                    break;
                case 5:
                    new GeneralCopier(context, AFR53_DATABASE_NAME).initializeCopier(3);
                    break;
                case 6:
                    new GeneralCopier(context, DN1933_DATABASE_NAME).initializeCopier(3);
                    break;
                case 7:
                    new GeneralCopier(context, UKJV_DATABASE_NAME).initializeCopier(1);
                    break;
                case 8:
                    new GeneralCopier(context, WEBBE_DATABASE_NAME).initializeCopier(1);
                    break;
                case 9:
                    new GeneralCopier(context, AFR83_DATABASE_NAME).initializeCopier(3);
                    break;

            }

        }

        return null;

    }

}
