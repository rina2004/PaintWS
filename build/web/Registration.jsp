<%-- 
    Document   : Registration
    Created on : 25 Mar 2025, 13:18:55
    Author     : Rinaaaa
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Registration</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .error-message {
                color: #dc3545;
                font-size: 0.875rem;
                margin-top: 0.25rem;
            }
            .form-container {
                max-width: 600px;
                margin: 0 auto;
                padding: 2rem;
                border-radius: 0.5rem;
                box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
                background-color: #fff;
            }
            .required-field::after {
                content: "*";
                color: #dc3545;
                margin-left: 0.25rem;
            }
        </style>
    </head>
    <body class="bg-light">
        <div class="container py-5">
            <div class="form-container">
                <h2 class="text-center mb-4">Create an Account</h2>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger" role="alert">
                        ${errorMessage}
                    </div>
                </c:if>

                <form action="register" method="post" novalidate>
                    <!-- Username -->
                    <div class="mb-3">
                        <label for="username" class="form-label required-field">Username</label>
                        <input type="text" class="form-control ${not empty errors.username ? 'is-invalid' : ''}" 
                               id="username" name="username" value="${user.username}" required>
                        <c:if test="${not empty errors.username}">
                            <div class="error-message">${errors.username}</div>
                        </c:if>
                    </div>

                    <!-- Password -->
                    <div class="mb-3">
                        <label for="password" class="form-label required-field">Password</label>
                        <input type="password" class="form-control ${not empty errors.password ? 'is-invalid' : ''}" 
                               id="password" name="password" required>
                        <c:if test="${not empty errors.password}">
                            <div class="error-message" id="error-message-password">${errors.password}</div>
                        </c:if>
                    </div>

                    <!-- Confirm Password -->
                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label required-field">Confirm Password</label>
                        <input type="password" class="form-control ${not empty errors.confirmPassword ? 'is-invalid' : ''}" 
                               id="confirmPassword" name="confirmPassword" required>
                        <c:if test="${not empty errors.confirmPassword}">
                            <div class="error-message" id="error-message-comfirmpassword">${errors.confirmPassword}</div>
                        </c:if>
                    </div>

                    <!-- Full Name -->
                    <div class="mb-3">
                        <label for="fullname" class="form-label required-field">Full Name</label>
                        <input type="text" class="form-control ${not empty errors.fullname ? 'is-invalid' : ''}" 
                               id="fullname" name="fullname" value="${user.fullname}" required>
                        <c:if test="${not empty errors.fullname}">
                            <div class="error-message">${errors.fullname}</div>
                        </c:if>
                    </div>

                    <!-- Email -->
                    <div class="mb-3">
                        <label for="email" class="form-label required-field">Email</label>
                        <input type="email" class="form-control ${not empty errors.email ? 'is-invalid' : ''}" 
                               id="email" name="email" value="${user.email}" required>
                        <c:if test="${not empty errors.email}">
                            <div class="error-message">${errors.email}</div>
                        </c:if>
                    </div>

                    <!-- Country -->
                    <div class="mb-3">
                        <label for="country" class="form-label">Country</label>
                        <select class="form-select" id="country" name="country">
                            <option value="" data-code="">Select your country</option>
                            <option value="Afghanistan" data-code="+93" ${user.country == 'Afghanistan' ? 'selected' : ''}>Afghanistan</option>
                            <option value="Albania" data-code="+355" ${user.country == 'Albania' ? 'selected' : ''}>Albania</option>
                            <option value="Algeria" data-code="+213" ${user.country == 'Algeria' ? 'selected' : ''}>Algeria</option>
                            <option value="Andorra" data-code="+376" ${user.country == 'Andorra' ? 'selected' : ''}>Andorra</option>
                            <option value="Angola" data-code="+244" ${user.country == 'Angola' ? 'selected' : ''}>Angola</option>
                            <option value="Argentina" data-code="+54" ${user.country == 'Argentina' ? 'selected' : ''}>Argentina</option>
                            <option value="Armenia" data-code="+374" ${user.country == 'Armenia' ? 'selected' : ''}>Armenia</option>
                            <option value="Australia" data-code="+61" ${user.country == 'Australia' ? 'selected' : ''}>Australia</option>
                            <option value="Austria" data-code="+43" ${user.country == 'Austria' ? 'selected' : ''}>Austria</option>
                            <option value="Azerbaijan" data-code="+994" ${user.country == 'Azerbaijan' ? 'selected' : ''}>Azerbaijan</option>
                            <option value="Bahamas" data-code="+1" ${user.country == 'Bahamas' ? 'selected' : ''}>Bahamas</option>
                            <option value="Bahrain" data-code="+973" ${user.country == 'Bahrain' ? 'selected' : ''}>Bahrain</option>
                            <option value="Bangladesh" data-code="+880" ${user.country == 'Bangladesh' ? 'selected' : ''}>Bangladesh</option>
                            <option value="Belarus" data-code="+375" ${user.country == 'Belarus' ? 'selected' : ''}>Belarus</option>
                            <option value="Belgium" data-code="+32" ${user.country == 'Belgium' ? 'selected' : ''}>Belgium</option>
                            <option value="Belize" data-code="+501" ${user.country == 'Belize' ? 'selected' : ''}>Belize</option>
                            <option value="Benin" data-code="+229" ${user.country == 'Benin' ? 'selected' : ''}>Benin</option>
                            <option value="Bhutan" data-code="+975" ${user.country == 'Bhutan' ? 'selected' : ''}>Bhutan</option>
                            <option value="Bolivia" data-code="+591" ${user.country == 'Bolivia' ? 'selected' : ''}>Bolivia</option>
                            <option value="Bosnia and Herzegovina" data-code="+387" ${user.country == 'Bosnia and Herzegovina' ? 'selected' : ''}>Bosnia and Herzegovina</option>
                            <option value="Botswana" data-code="+267" ${user.country == 'Botswana' ? 'selected' : ''}>Botswana</option>
                            <option value="Brazil" data-code="+55" ${user.country == 'Brazil' ? 'selected' : ''}>Brazil</option>
                            <option value="Brunei" data-code="+673" ${user.country == 'Brunei' ? 'selected' : ''}>Brunei</option>
                            <option value="Bulgaria" data-code="+359" ${user.country == 'Bulgaria' ? 'selected' : ''}>Bulgaria</option>
                            <option value="Burkina Faso" data-code="+226" ${user.country == 'Burkina Faso' ? 'selected' : ''}>Burkina Faso</option>
                            <option value="Burundi" data-code="+257" ${user.country == 'Burundi' ? 'selected' : ''}>Burundi</option>
                            <option value="Cambodia" data-code="+855" ${user.country == 'Cambodia' ? 'selected' : ''}>Cambodia</option>
                            <option value="Cameroon" data-code="+237" ${user.country == 'Cameroon' ? 'selected' : ''}>Cameroon</option>
                            <option value="Canada" data-code="+1" ${user.country == 'Canada' ? 'selected' : ''}>Canada</option>
                            <option value="Cape Verde" data-code="+238" ${user.country == 'Cape Verde' ? 'selected' : ''}>Cape Verde</option>
                            <option value="Central African Republic" data-code="+236" ${user.country == 'Central African Republic' ? 'selected' : ''}>Central African Republic</option>
                            <option value="Chad" data-code="+235" ${user.country == 'Chad' ? 'selected' : ''}>Chad</option>
                            <option value="Chile" data-code="+56" ${user.country == 'Chile' ? 'selected' : ''}>Chile</option>
                            <option value="China" data-code="+86" ${user.country == 'China' ? 'selected' : ''}>China</option>
                            <option value="Colombia" data-code="+57" ${user.country == 'Colombia' ? 'selected' : ''}>Colombia</option>
                            <option value="Comoros" data-code="+269" ${user.country == 'Comoros' ? 'selected' : ''}>Comoros</option>
                            <option value="Congo" data-code="+242" ${user.country == 'Congo' ? 'selected' : ''}>Congo</option>
                            <option value="Costa Rica" data-code="+506" ${user.country == 'Costa Rica' ? 'selected' : ''}>Costa Rica</option>
                            <option value="Croatia" data-code="+385" ${user.country == 'Croatia' ? 'selected' : ''}>Croatia</option>
                            <option value="Cuba" data-code="+53" ${user.country == 'Cuba' ? 'selected' : ''}>Cuba</option>
                            <option value="Cyprus" data-code="+357" ${user.country == 'Cyprus' ? 'selected' : ''}>Cyprus</option>
                            <option value="Czech Republic" data-code="+420" ${user.country == 'Czech Republic' ? 'selected' : ''}>Czech Republic</option>
                            <option value="Denmark" data-code="+45" ${user.country == 'Denmark' ? 'selected' : ''}>Denmark</option>
                            <option value="Djibouti" data-code="+253" ${user.country == 'Djibouti' ? 'selected' : ''}>Djibouti</option>
                            <option value="Dominican Republic" data-code="+1" ${user.country == 'Dominican Republic' ? 'selected' : ''}>Dominican Republic</option>
                            <option value="Ecuador" data-code="+593" ${user.country == 'Ecuador' ? 'selected' : ''}>Ecuador</option>
                            <option value="Egypt" data-code="+20" ${user.country == 'Egypt' ? 'selected' : ''}>Egypt</option>
                            <option value="El Salvador" data-code="+503" ${user.country == 'El Salvador' ? 'selected' : ''}>El Salvador</option>
                            <option value="Equatorial Guinea" data-code="+240" ${user.country == 'Equatorial Guinea' ? 'selected' : ''}>Equatorial Guinea</option>
                            <option value="Eritrea" data-code="+291" ${user.country == 'Eritrea' ? 'selected' : ''}>Eritrea</option>
                            <option value="Estonia" data-code="+372" ${user.country == 'Estonia' ? 'selected' : ''}>Estonia</option>
                            <option value="Ethiopia" data-code="+251" ${user.country == 'Ethiopia' ? 'selected' : ''}>Ethiopia</option>
                            <option value="Fiji" data-code="+679" ${user.country == 'Fiji' ? 'selected' : ''}>Fiji</option>
                            <option value="Finland" data-code="+358" ${user.country == 'Finland' ? 'selected' : ''}>Finland</option>
                            <option value="France" data-code="+33" ${user.country == 'France' ? 'selected' : ''}>France</option>
                            <option value="Gabon" data-code="+241" ${user.country == 'Gabon' ? 'selected' : ''}>Gabon</option>
                            <option value="Gambia" data-code="+220" ${user.country == 'Gambia' ? 'selected' : ''}>Gambia</option>
                            <option value="Georgia" data-code="+995" ${user.country == 'Georgia' ? 'selected' : ''}>Georgia</option>
                            <option value="Germany" data-code="+49" ${user.country == 'Germany' ? 'selected' : ''}>Germany</option>
                            <option value="Ghana" data-code="+233" ${user.country == 'Ghana' ? 'selected' : ''}>Ghana</option>
                            <option value="Greece" data-code="+30" ${user.country == 'Greece' ? 'selected' : ''}>Greece</option>
                            <option value="Guatemala" data-code="+502" ${user.country == 'Guatemala' ? 'selected' : ''}>Guatemala</option>
                            <option value="Guinea" data-code="+224" ${user.country == 'Guinea' ? 'selected' : ''}>Guinea</option>
                            <option value="Guinea-Bissau" data-code="+245" ${user.country == 'Guinea-Bissau' ? 'selected' : ''}>Guinea-Bissau</option>
                            <option value="Haiti" data-code="+509" ${user.country == 'Haiti' ? 'selected' : ''}>Haiti</option>
                            <option value="Honduras" data-code="+504" ${user.country == 'Honduras' ? 'selected' : ''}>Honduras</option>
                            <option value="Hong Kong" data-code="+852" ${user.country == 'Hong Kong' ? 'selected' : ''}>Hong Kong</option>
                            <option value="Hungary" data-code="+36" ${user.country == 'Hungary' ? 'selected' : ''}>Hungary</option>
                            <option value="Iceland" data-code="+354" ${user.country == 'Iceland' ? 'selected' : ''}>Iceland</option>
                            <option value="India" data-code="+91" ${user.country == 'India' ? 'selected' : ''}>India</option>
                            <option value="Indonesia" data-code="+62" ${user.country == 'Indonesia' ? 'selected' : ''}>Indonesia</option>
                            <option value="Iran" data-code="+98" ${user.country == 'Iran' ? 'selected' : ''}>Iran</option>
                            <option value="Iraq" data-code="+964" ${user.country == 'Iraq' ? 'selected' : ''}>Iraq</option>
                            <option value="Ireland" data-code="+353" ${user.country == 'Ireland' ? 'selected' : ''}>Ireland</option>
                            <option value="Israel" data-code="+972" ${user.country == 'Israel' ? 'selected' : ''}>Israel</option>
                            <option value="Italy" data-code="+39" ${user.country == 'Italy' ? 'selected' : ''}>Italy</option>
                            <option value="Jamaica" data-code="+1" ${user.country == 'Jamaica' ? 'selected' : ''}>Jamaica</option>
                            <option value="Japan" data-code="+81" ${user.country == 'Japan' ? 'selected' : ''}>Japan</option>
                            <option value="Jordan" data-code="+962" ${user.country == 'Jordan' ? 'selected' : ''}>Jordan</option>
                            <option value="Kazakhstan" data-code="+7" ${user.country == 'Kazakhstan' ? 'selected' : ''}>Kazakhstan</option>
                            <option value="Kenya" data-code="+254" ${user.country == 'Kenya' ? 'selected' : ''}>Kenya</option>
                            <option value="Kiribati" data-code="+686" ${user.country == 'Kiribati' ? 'selected' : ''}>Kiribati</option>
                            <option value="North Korea" data-code="+850" ${user.country == 'North Korea' ? 'selected' : ''}>North Korea</option>
                            <option value="South Korea" data-code="+82" ${user.country == 'South Korea' ? 'selected' : ''}>South Korea</option>
                            <option value="Kuwait" data-code="+965" ${user.country == 'Kuwait' ? 'selected' : ''}>Kuwait</option>
                            <option value="Kyrgyzstan" data-code="+996" ${user.country == 'Kyrgyzstan' ? 'selected' : ''}>Kyrgyzstan</option>
                            <option value="Laos" data-code="+856" ${user.country == 'Laos' ? 'selected' : ''}>Laos</option>
                            <option value="Latvia" data-code="+371" ${user.country == 'Latvia' ? 'selected' : ''}>Latvia</option>
                            <option value="Lebanon" data-code="+961" ${user.country == 'Lebanon' ? 'selected' : ''}>Lebanon</option>
                            <option value="Lesotho" data-code="+266" ${user.country == 'Lesotho' ? 'selected' : ''}>Lesotho</option>
                            <option value="Liberia" data-code="+231" ${user.country == 'Liberia' ? 'selected' : ''}>Liberia</option>
                            <option value="Libya" data-code="+218" ${user.country == 'Libya' ? 'selected' : ''}>Libya</option>
                            <option value="Liechtenstein" data-code="+423" ${user.country == 'Liechtenstein' ? 'selected' : ''}>Liechtenstein</option>                            <option value="Lithuania" data-code="+370" ${user.country == 'Lithuania' ? 'selected' : ''}>Lithuania</option>
                            <option value="Luxembourg" data-code="+352" ${user.country == 'Luxembourg' ? 'selected' : ''}>Luxembourg</option
                            <option value="North Macedonia" data-code="+389" ${user.country == 'North Macedonia' ? 'selected' : ''}>North Macedonia</option>
                            <option value="Madagascar" data-code="+261" ${user.country == 'Madagascar' ? 'selected' : ''}>Madagascar</option>
                            <option value="Malawi" data-code="+265" ${user.country == 'Malawi' ? 'selected' : ''}>Malawi</option>
                            <option value="Malaysia" data-code="+60" ${user.country == 'Malaysia' ? 'selected' : ''}>Malaysia</option>
                            <option value="Maldives" data-code="+960" ${user.country == 'Maldives' ? 'selected' : ''}>Maldives</option>
                            <option value="Mali" data-code="+223" ${user.country == 'Mali' ? 'selected' : ''}>Mali</option>
                            <option value="Malta" data-code="+356" ${user.country == 'Malta' ? 'selected' : ''}>Malta</option>
                            <option value="Marshall Islands" data-code="+692" ${user.country == 'Marshall Islands' ? 'selected' : ''}>Marshall Islands</option>
                            <option value="Mauritania" data-code="+222" ${user.country == 'Mauritania' ? 'selected' : ''}>Mauritania</option>
                            <option value="Mauritius" data-code="+230" ${user.country == 'Mauritius' ? 'selected' : ''}>Mauritius</option>
                            <option value="Mexico" data-code="+52" ${user.country == 'Mexico' ? 'selected' : ''}>Mexico</option>
                            <option value="Micronesia" data-code="+691" ${user.country == 'Micronesia' ? 'selected' : ''}>Micronesia</option>
                            <option value="Moldova" data-code="+373" ${user.country == 'Moldova' ? 'selected' : ''}>Moldova</option>
                            <option value="Monaco" data-code="+377" ${user.country == 'Monaco' ? 'selected' : ''}>Monaco</option>
                            <option value="Mongolia" data-code="+976" ${user.country == 'Mongolia' ? 'selected' : ''}>Mongolia</option>
                            <option value="Montenegro" data-code="+382" ${user.country == 'Montenegro' ? 'selected' : ''}>Montenegro</option>
                            <option value="Morocco" data-code="+212" ${user.country == 'Morocco' ? 'selected' : ''}>Morocco</option>
                            <option value="Mozambique" data-code="+258" ${user.country == 'Mozambique' ? 'selected' : ''}>Mozambique</option>
                            <option value="Myanmar" data-code="+95" ${user.country == 'Myanmar' ? 'selected' : ''}>Myanmar</option>
                            <option value="Namibia" data-code="+264" ${user.country == 'Namibia' ? 'selected' : ''}>Namibia</option>
                            <option value="Nauru" data-code="+674" ${user.country == 'Nauru' ? 'selected' : ''}>Nauru</option>
                            <option value="Nepal" data-code="+977" ${user.country == 'Nepal' ? 'selected' : ''}>Nepal</option>
                            <option value="Netherlands" data-code="+31" ${user.country == 'Netherlands' ? 'selected' : ''}>Netherlands</option>
                            <option value="New Zealand" data-code="+64" ${user.country == 'New Zealand' ? 'selected' : ''}>New Zealand</option>
                            <option value="Nicaragua" data-code="+505" ${user.country == 'Nicaragua' ? 'selected' : ''}>Nicaragua</option>
                            <option value="Niger" data-code="+227" ${user.country == 'Niger' ? 'selected' : ''}>Niger</option>
                            <option value="Nigeria" data-code="+234" ${user.country == 'Nigeria' ? 'selected' : ''}>Nigeria</option>
                            <option value="Norway" data-code="+47" ${user.country == 'Norway' ? 'selected' : ''}>Norway</option>
                            <option value="Oman" data-code="+968" ${user.country == 'Oman' ? 'selected' : ''}>Oman</option>
                            <option value="Pakistan" data-code="+92" ${user.country == 'Pakistan' ? 'selected' : ''}>Pakistan</option>
                            <option value="Palau" data-code="+680" ${user.country == 'Palau' ? 'selected' : ''}>Palau</option>
                            <option value="Palestine" data-code="+970" ${user.country == 'Palestine' ? 'selected' : ''}>Palestine</option>
                            <option value="Panama" data-code="+507" ${user.country == 'Panama' ? 'selected' : ''}>Panama</option>
                            <option value="Papua New Guinea" data-code="+675" ${user.country == 'Papua New Guinea' ? 'selected' : ''}>Papua New Guinea</option>
                            <option value="Paraguay" data-code="+595" ${user.country == 'Paraguay' ? 'selected' : ''}>Paraguay</option>
                            <option value="Peru" data-code="+51" ${user.country == 'Peru' ? 'selected' : ''}>Peru</option>
                            <option value="Philippines" data-code="+63" ${user.country == 'Philippines' ? 'selected' : ''}>Philippines</option>
                            <option value="Poland" data-code="+48" ${user.country == 'Poland' ? 'selected' : ''}>Poland</option>
                            <option value="Portugal" data-code="+351" ${user.country == 'Portugal' ? 'selected' : ''}>Portugal</option>
                            <option value="Qatar" data-code="+974" ${user.country == 'Qatar' ? 'selected' : ''}>Qatar</option>
                            <option value="Romania" data-code="+40" ${user.country == 'Romania' ? 'selected' : ''}>Romania</option>
                            <option value="Russia" data-code="+7" ${user.country == 'Russia' ? 'selected' : ''}>Russia</option>
                            <option value="Rwanda" data-code="+250" ${user.country == 'Rwanda' ? 'selected' : ''}>Rwanda</option>
                            <option value="Samoa" data-code="+685" ${user.country == 'Samoa' ? 'selected' : ''}>Samoa</option>
                            <option value="San Marino" data-code="+378" ${user.country == 'San Marino' ? 'selected' : ''}>San Marino</option>
                            <option value="Sao Tome and Principe" data-code="+239" ${user.country == 'Sao Tome and Principe' ? 'selected' : ''}>Sao Tome and Principe</option>
                            <option value="Saudi Arabia" data-code="+966" ${user.country == 'Saudi Arabia' ? 'selected' : ''}>Saudi Arabia</option>
                            <option value="Senegal" data-code="+221" ${user.country == 'Senegal' ? 'selected' : ''}>Senegal</option>
                            <option value="Serbia" data-code="+381" ${user.country == 'Serbia' ? 'selected' : ''}>Serbia</option>
                            <option value="Seychelles" data-code="+248" ${user.country == 'Seychelles' ? 'selected' : ''}>Seychelles</option>
                            <option value="Sierra Leone" data-code="+232" ${user.country == 'Sierra Leone' ? 'selected' : ''}>Sierra Leone</option>
                            <option value="Singapore" data-code="+65" ${user.country == 'Singapore' ? 'selected' : ''}>Singapore</option>
                            <option value="Slovakia" data-code="+421" ${user.country == 'Slovakia' ? 'selected' : ''}>Slovakia</option>
                            <option value="Slovenia" data-code="+386" ${user.country == 'Slovenia' ? 'selected' : ''}>Slovenia</option>
                            <option value="Solomon Islands" data-code="+677" ${user.country == 'Solomon Islands' ? 'selected' : ''}>Solomon Islands</option>
                            <option value="Somalia" data-code="+252" ${user.country == 'Somalia' ? 'selected' : ''}>Somalia</option>
                            <option value="South Africa" data-code="+27" ${user.country == 'South Africa' ? 'selected' : ''}>South Africa</option>
                            <option value="South Sudan" data-code="+211" ${user.country == 'South Sudan' ? 'selected' : ''}>South Sudan</option>
                            <option value="Spain" data-code="+34" ${user.country == 'Spain' ? 'selected' : ''}>Spain</option>
                            <option value="Sri Lanka" data-code="+94" ${user.country == 'Sri Lanka' ? 'selected' : ''}>Sri Lanka</option>
                            <option value="Sudan" data-code="+249" ${user.country == 'Sudan' ? 'selected' : ''}>Sudan</option>
                            <option value="Suriname" data-code="+597" ${user.country == 'Suriname' ? 'selected' : ''}>Suriname</option>
                            <option value="Eswatini" data-code="+268" ${user.country == 'Eswatini' ? 'selected' : ''}>Eswatini</option>
                            <option value="Sweden" data-code="+46" ${user.country == 'Sweden' ? 'selected' : ''}>Sweden</option>
                            <option value="Switzerland" data-code="+41" ${user.country == 'Switzerland' ? 'selected' : ''}>Switzerland</option>
                            <option value="Syria" data-code="+963" ${user.country == 'Syria' ? 'selected' : ''}>Syria</option>
                            <option value="Taiwan" data-code="+886" ${user.country == 'Taiwan' ? 'selected' : ''}>Taiwan</option>
                            <option value="Tajikistan" data-code="+992" ${user.country == 'Tajikistan' ? 'selected' : ''}>Tajikistan</option>
                            <option value="Tanzania" data-code="+255" ${user.country == 'Tanzania' ? 'selected' : ''}>Tanzania</option>
                            <option value="Thailand" data-code="+66" ${user.country == 'Thailand' ? 'selected' : ''}>Thailand</option>
                            <option value="Timor-Leste" data-code="+670" ${user.country == 'Timor-Leste' ? 'selected' : ''}>Timor-Leste</option>
                            <option value="Togo" data-code="+228" ${user.country == 'Togo' ? 'selected' : ''}>Togo</option>
                            <option value="Tonga" data-code="+676" ${user.country == 'Tonga' ? 'selected' : ''}>Tonga</option>
                            <option value="Trinidad and Tobago" data-code="+1" ${user.country == 'Trinidad and Tobago' ? 'selected' : ''}>Trinidad and Tobago</option>
                            <option value="Tunisia" data-code="+216" ${user.country == 'Tunisia' ? 'selected' : ''}>Tunisia</option>
                            <option value="Turkey" data-code="+90" ${user.country == 'Turkey' ? 'selected' : ''}>Turkey</option>
                            <option value="Turkmenistan" data-code="+993" ${user.country == 'Turkmenistan' ? 'selected' : ''}>Turkmenistan</option>
                            <option value="Tuvalu" data-code="+688" ${user.country == 'Tuvalu' ? 'selected' : ''}>Tuvalu</option>
                            <option value="Uganda" data-code="+256" ${user.country == 'Uganda' ? 'selected' : ''}>Uganda</option>
                            <option value="Ukraine" data-code="+380" ${user.country == 'Ukraine' ? 'selected' : ''}>Ukraine</option>
                            <option value="United Arab Emirates" data-code="+971" ${user.country == 'United Arab Emirates' ? 'selected' : ''}>United Arab Emirates</option>
                            <option value="United Kingdom" data-code="+44" ${user.country == 'United Kingdom' ? 'selected' : ''}>United Kingdom</option>
                            <option value="United States" data-code="+1" ${user.country == 'United States' ? 'selected' : ''}>United States</option>
                            <option value="Uruguay" data-code="+598" ${user.country == 'Uruguay' ? 'selected' : ''}>Uruguay</option>
                            <option value="Uzbekistan" data-code="+998" ${user.country == 'Uzbekistan' ? 'selected' : ''}>Uzbekistan</option>
                            <option value="Vanuatu" data-code="+678" ${user.country == 'Vanuatu' ? 'selected' : ''}>Vanuatu</option>
                            <option value="Vatican City" data-code="+39" ${user.country == 'Vatican City' ? 'selected' : ''}>Vatican City</option>
                            <option value="Venezuela" data-code="+58" ${user.country == 'Venezuela' ? 'selected' : ''}>Venezuela</option>
                            <option value="Vietnam" data-code="+84" ${user.country == 'Vietnam' ? 'selected' : ''}>Vietnam</option>
                            <option value="Yemen" data-code="+967" ${user.country == 'Yemen' ? 'selected' : ''}>Yemen</option>
                            <option value="Zambia" data-code="+260" ${user.country == 'Zambia' ? 'selected' : ''}>Zambia</option>
                            <option value="Zimbabwe" data-code="+263" ${user.country == 'Zimbabwe' ? 'selected' : ''}>Zimbabwe</option>
                        </select>
                    </div>

                    <!-- Phone Number -->
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Phone Number</label>
                        <div class="input-group">
                            <span class="input-group-text" id="phone-prefix"></span>
                            <input type="tel" class="form-control ${not empty errors.phoneNumber ? 'is-invalid' : ''}" 
                                   id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}">
                        </div>
                        <c:if test="${not empty errors.phoneNumber}">
                            <div class="error-message">${errors.phoneNumber}</div>
                        </c:if>
                        <div class="form-text">Enter your number without country code</div>
                    </div>

                    <!-- Terms and Conditions -->
                    <div class="mb-3 form-check">
                        <input type="checkbox" class="form-check-input ${not empty errors.acceptTerms ? 'is-invalid' : ''}" 
                               id="acceptTerms" name="acceptTerms" ${user.acceptTerms ? 'checked' : ''} required>
                        <label class="form-check-label" for="acceptTerms">
                            I agree to the <a href="#" target="_blank">Terms and Conditions</a>
                        </label>
                        <c:if test="${not empty errors.acceptTerms}">
                            <div class="error-message">${errors.acceptTerms}</div>
                        </c:if>
                    </div>

                    <!-- Marketing Emails -->
                    <div class="mb-4 form-check">
                        <input type="checkbox" class="form-check-input" id="acceptMarketing" 
                               name="acceptMarketing" ${user.acceptMarketing ? 'checked' : ''}>
                        <label class="form-check-label" for="acceptMarketing">
                            I would like to receive marketing emails
                        </label>
                    </div>

                    <!-- Submit Button -->
                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary">Register</button>
                    </div>

                    <!-- Login Link -->
                    <div class="text-center mt-3">
                        Already have an account? <a href="login">Login</a>
                    </div>
                </form>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <!-- JavaScript for automatic phone prefix -->
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const countrySelect = document.getElementById('country');
                const phonePrefix = document.getElementById('phone-prefix');
                const phoneInput = document.getElementById('phoneNumber');

                // Function to update phone prefix based on selected country
                function updatePhonePrefix() {
                    const selectedOption = countrySelect.options[countrySelect.selectedIndex];
                    const countryCode = selectedOption.getAttribute('data-code') || '';

                    // Update the prefix display
                    phonePrefix.textContent = countryCode;

                    // Store the full prefix in a hidden field or data attribute if needed
                    phonePrefix.setAttribute('data-code', countryCode);
                }

                // Initialize phone prefix
                updatePhonePrefix();

                // Update phone prefix when country changes
                countrySelect.addEventListener('change', updatePhonePrefix);

                // Optional: Format phone number as user types
                phoneInput.addEventListener('input', function () {
                    // Remove any non-digit characters
                    this.value = this.value.replace(/\D/g, '');
                });
            });
        </script>

    </body>
</html>