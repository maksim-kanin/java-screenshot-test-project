<div class="carousel-item" data-mdb-interval="1000000000">
    <figure class="figure">
        <h4>
            <button class="badge rounded-pill badge-light" onclick="scaleDiff()">Zoom</button>
        </h4>
        <img id="${id}-diff" src="data:image/png;base64,${diff}"
             class="img-fluid"
             alt="Diff"/>
        <figcaption class="figure-caption">
            <h4>
                <button class="badge rounded-pill badge-warning" disabled>
                    <i class="fas fa-camera-retro"></i> Diff ${diffSize}px
                </button>
            </h4>
        </figcaption>
    </figure>
</div>