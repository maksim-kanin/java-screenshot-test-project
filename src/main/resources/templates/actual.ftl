<div class="carousel-item active" data-mdb-interval="1000000000">
    <figure class="figure">
        <img id="${id}-actual" src="data:image/png;base64,${actual}"
             class="rounded img-fluid"
             alt="Actual"/>
        <figcaption class="figure-caption">
            <h4>
                <button class="badge rounded-pill badge-danger" disabled>
                    <i class="fas fa-camera-retro"></i> Actual
                </button>
                <button id="${id}-update" class="badge rounded-pill badge-info"
                        data-mdb-toggle="modal" data-mdb-target="#${id}-modal">
                    <i class="fas fa-save"></i> Update
                </button>
            </h4>
        </figcaption>
    </figure>
</div>