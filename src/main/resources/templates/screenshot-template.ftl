<!DOCTYPE html>
<html lang="en" style="height: 100%">
<head>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/4.1.0/mdb.min.css" rel="stylesheet"/>
    <style>
        .figure-caption {
            padding: 10px;
        }

        .carousel-item {
            transform: scale(1);
        }
    </style>
</head>
<body style="height: 100%;">
<div class="container-fluid bg-dark" style="height: 100%;">
    <div class="row text-center" style="height: 100%; display: flex; align-items: center;">
        <#if reference??>
            <div class="col-md-2">
                <button class="carousel-control-prev" type="button" data-mdb-target="#${id}"
                        data-mdb-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
            </div>
        </#if>
        <div class="col">
            <div class="row">
                <div class="row" style="height: 30px;display: flex;justify-content: center; margin-top: 20px">
                    <div class="range" style="width: 50%">
                        <input type="range" class="form-range" min="0" max="2" step="0.1" id="slider"/>
                    </div>
                </div>
                <div class="row">
                    <div id="${id}" class="carousel slide carousel-fade">
                        <div class="carousel-inner">
                            <div class="carousel-item active" data-mdb-interval="1000000000">
                                <figure class="figure">
                                    <img src="data:image/png;base64,${actual}"
                                         class="rounded img-fluid"
                                         alt="Actual"/>
                                    <figcaption class="figure-caption">
                                        <h4>
                                            <span class="badge rounded-pill badge-danger">
                                                <i class="fas fa-camera-retro"></i> Actual</span>
                                        </h4>
                                    </figcaption>
                                </figure>
                            </div>
                            <#if reference??>
                                <div class="carousel-item" data-mdb-interval="1000000000">
                                    <figure class="figure">
                                        <img src="data:image/png;base64,${reference}"
                                             class="rounded img-fluid"
                                             alt="Reference"/>
                                        <figcaption class="figure-caption">
                                            <h4><span class="badge rounded-pill badge-success">
                                            <i class="fas fa-camera-retro"></i> Reference</span></h4>
                                        </figcaption>
                                    </figure>
                                </div>
                                <div class="carousel-item" data-mdb-interval="1000000000">
                                    <figure class="figure">
                                        <img src="data:image/png;base64,${diff}"
                                             class="rounded img-fluid"
                                             alt="Diff"/>
                                        <figcaption class="figure-caption">
                                            <h4><span class="badge rounded-pill badge-warning">
                                            <i class="fas fa-camera-retro"></i> Diff</span></h4>
                                        </figcaption>
                                    </figure>
                                </div>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <#if reference??>
            <div class="col-md-2">
                <button class="carousel-control-next" type="button" data-mdb-target="#${id}"
                        data-mdb-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </#if>
    </div>
</div>
<script>
    document.getElementById("slider").onchange = function (e) {
        let sliderValue = e.target.value;
        document.getElementById("${id}")
            .getElementsByClassName("carousel-item active")[0].style.transform = 'scale(' + sliderValue + ')';
    }
</script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/4.1.0/mdb.min.js"></script>
</body>
</html>