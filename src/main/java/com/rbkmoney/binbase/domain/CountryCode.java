package com.rbkmoney.binbase.domain;

import java.util.HashMap;
import java.util.Map;

public enum CountryCode {

    AF("Afghanistan", "AFG", "4"),
    AL("Albania", "ALB", "8"),
    AQ("Antarctica", "ATA", "10"),
    DZ("Algeria", "DZA", "12"),
    AS("American Samoa", "ASM", "16"),
    AD("Andorra", "AND", "20"),
    AO("Angola", "AGO", "24"),
    AG("Antigua and Barbuda", "ATG", "28"),
    AZ("Azerbaijan", "AZE", "31"),
    AR("Argentina", "ARG", "32"),
    AU("Australia", "AUS", "36"),
    AT("Austria", "AUT", "40"),
    BS("Bahamas", "BHS", "44"),
    BH("Bahrain", "BHR", "48"),
    BD("Bangladesh", "BGD", "50"),
    AM("Armenia", "ARM", "51"),
    BB("Barbados", "BRB", "52"),
    BE("Belgium", "BEL", "56"),
    BM("Bermuda", "BMU", "60"),
    BT("Bhutan", "BTN", "64"),
    BO("Plurinational State of Bolivia", "BOL", "68"),
    BA("Bosnia and Herzegovina", "BIH", "70"),
    BW("Botswana", "BWA", "72"),
    BV("Bouvet Island", "BVT", "74"),
    BR("Brazil", "BRA", "76"),
    BZ("Belize", "BLZ", "84"),
    IO("British Indian Ocean Territory", "IOT", "86"),
    SB("Solomon Islands", "SLB", "90"),
    VG("British Virgin Islands", "VGB", "92"),
    BN("Brunei Darussalam", "BRN", "96"),
    BG("Bulgaria", "BGR", "100"),
    MM("Myanmar", "MMR", "104"),
    BI("Burundi", "BDI", "108"),
    BY("Belarus", "BLR", "112"),
    KH("Cambodia", "KHM", "116"),
    CM("Cameroon", "CMR", "120"),
    CA("Canada", "CAN", "124"),
    CV("Cape Verde", "CPV", "132"),
    KY("Cayman Islands", "CYM", "136"),
    CF("Central African Republic", "CAF", "140"),
    LK("Sri Lanka", "LKA", "144"),
    TD("Chad", "TCD", "148"),
    CL("Chile", "CHL", "152"),
    CN("China", "CHN", "156"),
    TW("Taiwan, Province of China", "TWN", "158"),
    CX("Christmas Island", "CXR", "162"),
    CC("Cocos Islands", "CCK", "166"),
    CO("Colombia", "COL", "170"),
    KM("Comoros", "COM", "174"),
    YT("Mayotte", "MYT", "175"),
    CG("Congo", "COG", "178"),
    CD("The Democratic Republic of the Congo", "COD", "180"),
    CK("Cook Islands", "COK", "184"),
    CR("Costa Rica", "CRI", "188"),
    HR("Croatia", "HRV", "191"),
    CU("Cuba", "CUB", "192"),
    CY("Cyprus", "CYP", "196"),
    CZ("Czech Republic", "CZE", "203"),
    BJ("Benin", "BEN", "204"),
    DK("Denmark", "DNK", "208"),
    DM("Dominica", "DMA", "212"),
    DO("Dominican Republic", "DOM", "214"),
    EC("Ecuador", "ECU", "218"),
    SV("El Salvador", "SLV", "222"),
    GQ("Equatorial Guinea", "GNQ", "226"),
    ET("Ethiopia", "ETH", "231"),
    ER("Eritrea", "ERI", "232"),
    EE("Estonia", "EST", "233"),
    FO("Faroe Islands", "FRO", "234"),
    FK("Falkland Islands", "FLK", "238"),
    GS("South Georgia and the South Sandwich Islands", "SGS", "239"),
    FJ("Fiji", "FJI", "242"),
    FI("Finland", "FIN", "246"),
    AX("Åland Islands", "ALA", "248"),
    FR("France", "FRA", "250"),
    GF("French Guiana", "GUF", "254"),
    PF("French Polynesia", "PYF", "258"),
    TF("French Southern Territories", "ATF", "260"),
    DJ("Djibouti", "DJI", "262"),
    GA("Gabon", "GAB", "266"),
    GE("Georgia", "GEO", "268"),
    GM("Gambia", "GMB", "270"),
    PS("Occupied Palestinian Territory", "PSE", "275"),
    DE("Germany", "DEU", "276"),
    GH("Ghana", "GHA", "288"),
    GI("Gibraltar", "GIB", "292"),
    KI("Kiribati", "KIR", "296"),
    GR("Greece", "GRC", "300"),
    GL("Greenland", "GRL", "304"),
    GD("Grenada", "GRD", "308"),
    GP("Guadeloupe", "GLP", "312"),
    GU("Guam", "GUM", "316"),
    GT("Guatemala", "GTM", "320"),
    GN("Guinea", "GIN", "324"),
    GY("Guyana", "GUY", "328"),
    HT("Haiti", "HTI", "332"),
    HM("Heard Island and McDonald Islands", "HMD", "334"),
    VA("Holy See", "VAT", "336"),
    HN("Honduras", "HND", "340"),
    HK("Hong Kong", "HKG", "344"),
    HU("Hungary", "HUN", "348"),
    IS("Iceland", "ISL", "352"),
    IN("India", "IND", "356"),
    ID("Indonesia", "IDN", "360"),
    IR("Islamic Republic of Iran", "IRN", "364"),
    IQ("Iraq", "IRQ", "368"),
    IE("Ireland", "IRL", "372"),
    IL("Israel", "ISR", "376"),
    IT("Italy", "ITA", "380"),
    CI("Côte d'Ivoire", "CIV", "384"),
    JM("Jamaica", "JAM", "388"),
    JP("Japan", "JPN", "392"),
    KZ("Kazakhstan", "KAZ", "398"),
    JO("Jordan", "JOR", "400"),
    KE("Kenya", "KEN", "404"),
    KP("Democratic People's Republic of Korea", "PRK", "408"),
    KR("Republic of Korea", "KOR", "410"),
    KW("Kuwait", "KWT", "414"),
    KG("Kyrgyzstan", "KGZ", "417"),
    LA("Lao People's Democratic Republic", "LAO", "418"),
    LB("Lebanon", "LBN", "422"),
    LS("Lesotho", "LSO", "426"),
    LV("Latvia", "LVA", "428"),
    LR("Liberia", "LBR", "430"),
    LY("Libya", "LBY", "434"),
    LI("Liechtenstein", "LIE", "438"),
    LT("Lithuania", "LTU", "440"),
    LU("Luxembourg", "LUX", "442"),
    MO("Macao", "MAC", "446"),
    MG("Madagascar", "MDG", "450"),
    MW("Malawi", "MWI", "454"),
    MY("Malaysia", "MYS", "458"),
    MV("Maldives", "MDV", "462"),
    ML("Mali", "MLI", "466"),
    MT("Malta", "MLT", "470"),
    MQ("Martinique", "MTQ", "474"),
    MR("Mauritania", "MRT", "478"),
    MU("Mauritius", "MUS", "480"),
    MX("Mexico", "MEX", "484"),
    MC("Monaco", "MCO", "492"),
    MN("Mongolia", "MNG", "496"),
    MD("Republic of Moldova", "MDA", "498"),
    ME("Montenegro", "MNE", "499"),
    MS("Montserrat", "MSR", "500"),
    MA("Morocco", "MAR", "504"),
    MZ("Mozambique", "MOZ", "508"),
    OM("Oman", "OMN", "512"),
    NA("Namibia", "NAM", "516"),
    NR("Nauru", "NRU", "520"),
    NP("Nepal", "NPL", "524"),
    NL("Netherlands", "NLD", "528"),
    AN("Netherlands Antilles", "ANT", "530"),
    CW("Cura/u00E7ao", "CUW", "531"),
    AW("Aruba", "ABW", "533"),
    SX("Sint Maarten", "SXM", "534"),
    BQ("Bonaire, Sint Eustatius and Saba", "BES", "535"),
    NC("New Caledonia", "NCL", "540"),
    VU("Vanuatu", "VUT", "548"),
    NZ("New Zealand", "NZL", "554"),
    NI("Nicaragua", "NIC", "558"),
    NE("Niger", "NER", "562"),
    NG("Nigeria", "NGA", "566"),
    NU("Niue", "NIU", "570"),
    NF("Norfolk Island", "NFK", "574"),
    NO("Norway", "NOR", "578"),
    MP("Northern Mariana Islands", "MNP", "580"),
    UM("United States Minor Outlying Islands", "UMI", "581"),
    FM("Federated States of Micronesia", "FSM", "583"),
    MH("Marshall Islands", "MHL", "584"),
    PW("Palau", "PLW", "585"),
    PK("Pakistan", "PAK", "586"),
    PA("Panama", "PAN", "591"),
    PG("Papua New Guinea", "PNG", "598"),
    PY("Paraguay", "PRY", "600"),
    PE("Peru", "PER", "604"),
    PH("Philippines", "PHL", "608"),
    PN("Pitcairn", "PCN", "612"),
    PL("Poland", "POL", "616"),
    PT("Portugal", "PRT", "620"),
    GW("Guinea-Bissau", "GNB", "624"),
    TL("Timor-Leste", "TLS", "626"),
    PR("Puerto Rico", "PRI", "630"),
    QA("Qatar", "QAT", "634"),
    RE("Réunion", "REU", "638"),
    RO("Romania", "ROU", "642"),
    RU("Russian Federation", "RUS", "643"),
    RW("Rwanda", "RWA", "646"),
    BL("Saint Barthélemy", "BLM", "652"),
    SH("Saint Helena, Ascension and Tristan da Cunha", "SHN", "654"),
    KN("Saint Kitts and Nevis", "KNA", "659"),
    AI("Anguilla", "AIA", "660"),
    LC("Saint Lucia", "LCA", "662"),
    MF("Saint Martin", "MAF", "663"),
    PM("Saint Pierre and Miquelon", "SPM", "666"),
    VC("Saint Vincent and the Grenadines", "VCT", "670"),
    SM("San Marino", "SMR", "674"),
    ST("Sao Tome and Principe", "STP", "678"),
    SA("Saudi Arabia", "SAU", "682"),
    SN("Senegal", "SEN", "686"),
    RS("Serbia", "SRB", "688"),
    SC("Seychelles", "SYC", "690"),
    SL("Sierra Leone", "SLE", "694"),
    SG("Singapore", "SGP", "702"),
    SK("Slovakia", "SVK", "703"),
    VN("Viet Nam", "VNM", "704"),
    SI("Slovenia", "SVN", "705"),
    SO("Somalia", "SOM", "706"),
    ZA("South Africa", "ZAF", "710"),
    ZW("Zimbabwe", "ZWE", "716"),
    ES("Spain", "ESP", "724"),
    SS("South Sudan", "SSD", "728"),
    SD("Sudan", "SDN", "729"),
    EH("Western Sahara", "ESH", "732"),
    SR("Suriname", "SUR", "740"),
    SJ("Svalbard and Jan Mayen", "SJM", "744"),
    SZ("Swaziland", "SWZ", "748"),
    SE("Sweden", "SWE", "752"),
    CH("Switzerland", "CHE", "756"),
    SY("Syrian Arab Republic", "SYR", "760"),
    TJ("Tajikistan", "TJK", "762"),
    TH("Thailand", "THA", "764"),
    TG("Togo", "TGO", "768"),
    TK("Tokelau", "TKL", "772"),
    TO("Tonga", "TON", "776"),
    TT("Trinidad and Tobago", "TTO", "780"),
    AE("United Arab Emirates", "ARE", "784"),
    TN("Tunisia", "TUN", "788"),
    TR("Turkey", "TUR", "792"),
    TM("Turkmenistan", "TKM", "795"),
    TC("Turks and Caicos Islands", "TCA", "796"),
    TV("Tuvalu", "TUV", "798"),
    UG("Uganda", "UGA", "800"),
    UA("Ukraine", "UKR", "804"),
    MK("The former Yugoslav Republic of Macedonia", "MKD", "807"),
    EG("Egypt", "EGY", "818"),
    GB("United Kingdom", "GBR", "826"),
    GG("Guemsey", "GGY", "831"),
    JE("Jersey", "JEY", "832"),
    IM("Isle of Man", "IMN", "833"),
    TZ("United Republic of Tanzania", "TZA", "834"),
    US("United States", "USA", "840"),
    VI("Virgin Islands, U.S.", "VIR", "850"),
    BF("Burkina Faso", "BFA", "854"),
    UY("Uruguay", "URY", "858"),
    UZ("Uzbekistan", "UZB", "860"),
    VE("Bolivarian Republic of Venezuela", "VEN", "862"),
    WF("Wallis and Futuna", "WLF", "876"),
    WS("Samoa", "WSM", "882"),
    YE("Yemen", "YEM", "887"),
    ZM("Zambia", "ZMB", "894");


    private static final Map<String, CountryCode> alpha3Map = new HashMap<>();
    private static final Map<String, CountryCode> numericMap = new HashMap<>();

    static {
        for (CountryCode cc : values()) {
            alpha3Map.put(cc.getAlpha3(), cc);
            numericMap.put(cc.getNumeric(), cc);
        }
    }

    private final String name;
    private final String alpha3;
    private final String numeric;


    CountryCode(String name, String alpha3, String numeric) {
        this.name = name;
        this.alpha3 = alpha3;
        this.numeric = numeric;
    }

    public static CountryCode getByAlpha2Code(String code) {
        try {
            return Enum.valueOf(CountryCode.class, code);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static CountryCode getByAlpha3Code(String code) {
        return alpha3Map.get(code);
    }

    public static CountryCode getByNumericCode(String code) {
        return numericMap.get(code);
    }

    public String getName() {
        return name;
    }

    public String getAlpha2() {
        return name();
    }

    public String getAlpha3() {
        return alpha3;
    }

    public String getNumeric() {
        return numeric;
    }
}

