;
(function ($) {
    "use strict";

    $.fn.showRecommendations = function (recommendationsNumber) {
        return this.each(function () {
            var item = $(this);
            var profileStore = ContextHub ? ContextHub.getStore("profile") : null;
            var userData = profileStore.getItem('/');

            if( userData.hasOwnProperty('authorizableId') ) {
                var authorizableId = userData['authorizableId'];
                //console.log(authorizableId);
                recommendationsNumber = (recommendationsNumber != undefined && recommendationsNumber != '') ?
                    recommendationsNumber : 3;
                $.getJSON("/bin/recommend/mahout.json?userId=" + authorizableId + "&recommendationsCount=" + recommendationsNumber).
                    done(function(response){
                        if (response.length > 0) {
                            //Populate with Data
                            //console.log(response);
                            var recommendationsContainer = $('.show-recommendations');
                            recommendationsContainer.empty();

                            var titleHTML = '<h1 class="title">Products recommendations extratced using Apache Mahout : (User Based recommendations) </h1>';
                            recommendationsContainer.append(titleHTML);
                            $.each(response, function(n, item){
                                console.log(item);
                                var productInfoHTML = '<span class="product-recommendation recommend_product">'+
                                    '<a class="product-recommendation-link" href="/content/geometrixx-outdoors/en/men/shirts/bambara-cargo.html#mnapbc" title="'+ item.product_title +'" x-cq-linkchecker="skip">' +
                                    '<span class="product-recommendation-image"><img src="'+ item.product_ImageSrc +'" width="100%" alt="'+item.product_title +'">' +
                                    '</span><div class="description"><p><span>'+ item.product_description + '</span><strong>'+ item.product_Price +'</strong>' +
                                    '<strong>Preference : '+ item.Preference + '</strong></p>' +
                                    '<span class="bg"></span></div><span class="inset-shadow"></span></a></span>';
                                recommendationsContainer.append(productInfoHTML);
                            });
                        } else {
                            //No data to show
                            var recommendationsContainer = $('.show-recommendations');
                            recommendationsContainer.empty();
                            var noDataFound = "<h2> No products found using Mahout recommendations </h2>"

                            recommendationsContainer.append(noDataFound);
                        }
                    }).fail(function(jqxhr, textStatus, error){
                        var err = textStatus + ", " + error;
                        console.log("Request Failed: " + err);
                    });
            }
        });
    };
})(jQuery);
