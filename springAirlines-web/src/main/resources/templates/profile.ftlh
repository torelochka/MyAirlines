<#import "layouts/base.ftlh" as base>
<@base.main title="Profile">
    <main>
        <div class="container">
            <h1>Profile</h1>

            <#list bookings as booking>
                <div class="card mt-5 mb-5">
                    <h5 class="card-header">Booking Trip</h5>
                    <div class="card-body">
                        <h5 class="card-title">Time (minutes): ${booking.trip.time}, Cost: ${booking.trip.cost})</h5>
                        <p class="card-text">
                            <#list booking.trip.cities as trip_cities>
                                ${trip_cities.name} <#if trip_cities?has_next>-></#if>
                            </#list>
                        </p>
                    </div>
                </div>
            </#list>
        </div>
    </main>
</@base.main>

