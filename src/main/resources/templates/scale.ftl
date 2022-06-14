<script type="text/javascript">
    $.fn.scale = function () {
        let element = this;
        let className = element.attr('class');
        let clazz;
        if (!element.is('img')) {
            console.log('Target element is not an image.')
            return
        }

        let imageURL = element.attr('src')
        let nativeImage = new Image()
        nativeImage.src = element.attr('src')
        let options = {
            round: true,
            width: 200,
            height: 200,
            background: '#262626',
            shadow: '0 8px 17px 0 rgba(0, 0, 0, 1)',
            border: '6px solid #FFF',
            cursor: true,
            zIndex: 999999,
        }

        let lens = document.createElement('div')
        lens.id = '' + className + 'BlowupLens'

        $('body').append(lens)

        lens = $('#' + lens.id)

        lens.css({
            position: 'absolute',
            visibility: 'hidden',
            'pointer-events': 'none',
            zIndex: options.zIndex,
            width: options.width,
            height: options.height,
            border: options.border,
            background: options.background,
            'border-radius': options.round ? '50%' : 'none',
            'box-shadow': options.shadow,
            'background-repeat': 'no-repeat',
        })

        element.mouseenter(function () {
            clazz = element.attr('class')
            lens = $('#' + clazz + 'BlowupLens')
            lens.css('visibility', 'visible')
        })

        element.mousemove(function (e) {
            let lensX = e.pageX - options.width / 2
            let lensY = e.pageY - options.height / 2
            let relX = e.offsetX
            let relY = e.offsetY
            let zoomX = -Math.floor(
                (relX / element.width()) * nativeImage.width - options.width / 2
            )
            let zoomY = -Math.floor(
                (relY / element.height()) * nativeImage.height - options.height / 2
            )
            let bgSize = nativeImage.width

            lens.css({
                left: lensX,
                top: lensY,
                'background-image': 'url(' + imageURL + ')',
                'background-position': zoomX + ' ' + zoomY,
                'background-size': bgSize,
                'background-position-x': zoomX,
                'background-position-y': zoomY
            })
        })

        element.mouseleave(function () {
            document.onmousewheel = null
            lens.css('visibility', 'hidden')
        })
    }
</script>